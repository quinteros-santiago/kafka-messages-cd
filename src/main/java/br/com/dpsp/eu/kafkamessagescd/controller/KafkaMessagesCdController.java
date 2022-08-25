package br.com.dpsp.eu.kafkamessagescd.controller;

import br.com.dpsp.eu.kafkamessagescd.dtos.request.CreateMessageRequest;
import br.com.dpsp.eu.kafkamessagescd.dtos.request.DeleteMessageRequest;
import br.com.dpsp.eu.kafkamessagescd.service.KafkaMessagesCdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class KafkaMessagesCdController {

    private final KafkaMessagesCdService kafkaMessagesCdService;

    public KafkaMessagesCdController(KafkaMessagesCdService kafkaMessagesCdService) {
        this.kafkaMessagesCdService = kafkaMessagesCdService;
    }

    @PostMapping("/message")
    public ResponseEntity<Object> createMessage(@RequestBody CreateMessageRequest request) throws ExecutionException, InterruptedException {
        return kafkaMessagesCdService.createMessage(request);
    }

    @DeleteMapping("/message")
    public ResponseEntity<Object> deleteMessage(@RequestBody DeleteMessageRequest request) {
        return kafkaMessagesCdService.deleteMessage(request);
    }
}
