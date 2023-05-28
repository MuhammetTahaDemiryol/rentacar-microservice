package com.tahademiryol.commonpackage.configuration.kafka.producer;

import com.tahademiryol.commonpackage.utils.kafka.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig {
    @Bean
    KafkaProducer getkafkaProducer(KafkaTemplate<String, Object> template) {
        return new KafkaProducer(template);
    }
}
