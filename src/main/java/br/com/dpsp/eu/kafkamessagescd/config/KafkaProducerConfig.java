package br.com.dpsp.eu.kafkamessagescd.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final String bootstrapServersConfig;
    private final Boolean enableSaslJaas;
    private final String key;
    private final String secret;

    public KafkaProducerConfig(Environment environment) {
        this.bootstrapServersConfig = environment.getProperty("kafka.bootstrap.server");
        this.enableSaslJaas =  environment.getProperty("kafka.enable.sasl.jaas", Boolean.class);
        this.key = environment.getProperty("kafka.key");
        this.secret = environment.getProperty("kafka.secret");
    }

    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServersConfig);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    public void configureSaslJaasIfEnabled(Map<String, Object> configProps) {
        if (Boolean.TRUE.equals(enableSaslJaas)) {
            configProps.put("sasl.jaas.config",
                    "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + key + "\"   password=\"" + secret + "\";");
            configProps.put("security.protocol", "SASL_SSL");
            configProps.put("ssl.endpoint.identification.algorithm", "https");
            configProps.put("sasl.mechanism", "PLAIN");
        }
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
