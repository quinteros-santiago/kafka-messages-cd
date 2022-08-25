package br.com.dpsp.eu.kafkamessagescd.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaAdminConfig {

    private final String BOOTSTRAP_SERVERS_CONFIG;
    private final String KEY;
    private final String SECRET;

    public KafkaAdminConfig(Environment environment) {
        this.BOOTSTRAP_SERVERS_CONFIG = environment.getProperty("kafka.bootstrap.server");
        this.KEY = environment.getProperty("kafka.key");
        this.SECRET = environment.getProperty("kafka.secret");
    }

    @Bean
    public AdminClient adminClient() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.BOOTSTRAP_SERVERS_CONFIG);
        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + KEY + "\"   password=\"" + SECRET + "\";");
        configProps.put("security.protocol", "SASL_SSL");
        configProps.put("ssl.endpoint.identification.algorithm", "https");
        configProps.put("sasl.mechanism", "PLAIN");
        return AdminClient.create(configProps);
    }
}
