package br.com.dpsp.eu.kafkamessagescd.service;

import br.com.dpsp.eu.kafkamessagescd.dtos.request.CreateMessageRequest;
import br.com.dpsp.eu.kafkamessagescd.dtos.request.DeleteMessageRequest;
import br.com.dpsp.eu.kafkamessagescd.dtos.response.ResponseCreatedMessage;
import br.com.dpsp.eu.kafkamessagescd.dtos.response.ResponseDeletedMessage;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DeleteRecordsResult;
import org.apache.kafka.clients.admin.RecordsToDelete;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaMessagesCdService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AdminClient adminClient;
    private final String kafkaInputTopic;

    @Autowired
    public KafkaMessagesCdService(Environment environment, KafkaTemplate<String, String> kafkaTemplate, AdminClient adminClient) {
        this.kafkaInputTopic = environment.getProperty("kafka.input.topic");
        this.kafkaTemplate = kafkaTemplate;
        this.adminClient = adminClient;
    }

    public ResponseEntity<Object> createMessage(CreateMessageRequest request) throws ExecutionException, InterruptedException {
        boolean isNotDone = true;
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate
                .send(kafkaInputTopic, request.getKeyInStringFormat(), request.getValueInJSONFormat());

        while (isNotDone) {
            if (future.isDone()) {
                isNotDone = false;
            }
        }

        return ResponseEntity.ok(
                new ResponseCreatedMessage(
                        "Message was produced sucessfully.",
                        future.get().getRecordMetadata().topic(),
                        future.get().getRecordMetadata().partition(),
                        future.get().getRecordMetadata().offset(),
                        request.getKeyInStringFormat(),
                        request.getValueInJSONFormat()));
    }

    public ResponseEntity<Object> deleteMessage(DeleteMessageRequest request) {
        boolean isNotDone = true;

        Map<TopicPartition, RecordsToDelete> recordsToDelete = new HashMap<>();
        recordsToDelete.put(
                new TopicPartition(
                        request.getTopic(),
                        request.getPartition()),
                RecordsToDelete.beforeOffset(request.getOffset() + 1));

        DeleteRecordsResult deleteRecordsResult = adminClient.deleteRecords(recordsToDelete);

        while (isNotDone) {
            if (deleteRecordsResult.all().isDone()) {
                isNotDone = false;
            }
        }

        return ResponseEntity.ok(new ResponseDeletedMessage(
                "Message was deleted sucessfully.",
                request.getTopic(),
                request.getPartition(),
                request.getOffset()));
    }
}
