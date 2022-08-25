package br.com.dpsp.eu.kafkamessagescd.dtos.request;

public class DeleteMessageRequest {

    private final String topic;
    private final int partition;
    private final int offset;

    public DeleteMessageRequest(String topic, int partition, int offset) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "DeleteMessageRequest{" +
                "topic='" + topic + '\'' +
                ", partition=" + partition +
                ", offset=" + offset +
                '}';
    }
}
