package com.mycompany.myapp.wire.kafka.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

  @Value("${kafka.bootstrap.servers:localhost:9092}")
  private String bootstrapServers;

  @Value("${kafka.polling.timeout:10000}")
  private Integer pollingTimeout;

  private Map<String, Object> consumer = new HashMap<>();

  private Map<String, Object> producer = new HashMap<>();

  @PostConstruct
  public void init() {
    consumer.computeIfAbsent(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, k -> bootstrapServers);
    producer.computeIfAbsent(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, k -> bootstrapServers);
  }

  public Map<String, Object> getConsumer() {
    return this.consumer;
  }

  public void setConsumer(final Map<String, Object> consumer) {
    this.consumer = consumer;
  }

  public Map<String, Object> getProducer() {
    return this.producer;
  }

  public void setProducer(final Map<String, Object> producer) {
    this.producer = producer;
  }

  public Integer getPollingTimeout() {
    return this.pollingTimeout;
  }

  public void setPollingTimeout(final Integer pollingTimeout) {
    this.pollingTimeout = pollingTimeout;
  }
}
