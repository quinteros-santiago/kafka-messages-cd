package br.com.dpsp.eu.kafkamessagescd.dtos.response;

public class ResponseCreatedMessage extends Response {

    private final String kafkaKey;
    private final String kafkaValue;

    public ResponseCreatedMessage(String message, String topic, int partition, long offset, String kafkaKey, String kafkaValue) {
        super(message, topic, partition, offset);
        this.kafkaKey = kafkaKey;
        this.kafkaValue = kafkaValue;
    }

    public String getKafkaKey() {
        return kafkaKey;
    }

    public String getKafkaValue() {
        return kafkaValue;
    }
}
