package br.com.dpsp.eu.kafkamessagescd.dtos.request;

public class CreateMessageRequest {

    private final String someKey;
    private final String someValue0;
    private final String someValue1;
    private final String someValue2;
    private final String someValue3;

    public CreateMessageRequest(String someKey, String someValue0, String someValue1, String someValue2, String someValue3) {
        this.someKey = someKey;
        this.someValue0 = someValue0;
        this.someValue1 = someValue1;
        this.someValue2 = someValue2;
        this.someValue3 = someValue3;
    }

    public String getKeyInStringFormat() {
        return '"' + someKey + '"';
    }

    public String getValueInJSONFormat() {
        return "{" +
                    '"' + "singleObject" + '"' + ":" + '"' + someValue0 + '"' + "," +
                    '"' + "singleObject" + '"' + ":" + '"' + someValue1 + '"' + "," +
                    '"' + "class" + '"' + ":" + "{" +
                        '"' + "objectOfClass" + '"' + ":" + '"' + someValue2 + '"' + "," +
                        '"' + "objectOfClass" + '"' + ":" + '"' + someValue3 + '"' +
                    "}" +
                "}";
    }
}

