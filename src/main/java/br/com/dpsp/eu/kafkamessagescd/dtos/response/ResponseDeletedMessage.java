package br.com.dpsp.eu.kafkamessagescd.dtos.response;

public class ResponseDeletedMessage extends Response {

    public ResponseDeletedMessage(String message, String topic, int partition, int offset) {
        super(message, topic, partition, offset);
    }
}
