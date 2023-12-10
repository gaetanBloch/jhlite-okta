package com.mycompany.myapp.wire.kafka.infrastructure.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KafkaConfiguration {

  @Bean
  public KafkaConsumer<String, String> kafkaConsumer(KafkaProperties kafkaProperties) {
    return new KafkaConsumer<>(kafkaProperties.getConsumer());
  }

  @Bean
  public KafkaProducer<String, String> kafkaProducer(KafkaProperties kafkaProperties) {
    return new KafkaProducer<>(kafkaProperties.getProducer());
  }
}
