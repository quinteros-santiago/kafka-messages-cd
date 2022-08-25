package br.com.dpsp.eu.kafkamessagescd.dtos.response;

public class Response {

    private final String message;
    private final String topic;
    private final int partition;
    private final long offset;

    public Response(String message, String topic, int partition, long offset) {
        this.message = message;
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
    }

    public String getMessage() {
        return message;
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                ", partition=" + partition +
                ", offset=" + offset +
                '}';
    }
}
