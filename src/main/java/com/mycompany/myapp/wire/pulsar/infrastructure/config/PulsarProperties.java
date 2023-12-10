package com.mycompany.myapp.wire.pulsar.infrastructure.config;

import org.apache.pulsar.client.impl.conf.ClientConfigurationData;
import org.apache.pulsar.client.impl.conf.ConsumerConfigurationData;
import org.apache.pulsar.client.impl.conf.ProducerConfigurationData;
import org.apache.pulsar.shade.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.pulsar.shade.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "pulsar")
public class PulsarProperties {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private Map<String, Object> client = new HashMap<>();
  private Map<String, Object> consumer = new HashMap<>();
  private Map<String, Object> producer = new HashMap<>();

  public Map<String, Object> getClientProps() {
    return client;
  }

  public void setClient(ClientConfigurationData clientConfig) throws JsonProcessingException {
    String json = OBJECT_MAPPER.writeValueAsString(clientConfig);
    this.client = OBJECT_MAPPER.readerForMapOf(Object.class).readValue(json);
  }

  public Map<String, Object> getConsumerProps() {
    return consumer;
  }

  public void setConsumer(ConsumerConfigurationData<?> consumerConfig) throws JsonProcessingException {
    String json = OBJECT_MAPPER.writeValueAsString(consumerConfig);
    this.consumer = OBJECT_MAPPER.readerForMapOf(Object.class).readValue(json);
  }

  public Map<String, Object> getProducerProps() {
    return producer;
  }

  public void setProducer(ProducerConfigurationData producerConfig) throws JsonProcessingException {
    String json = OBJECT_MAPPER.writeValueAsString(producerConfig);
    this.producer = OBJECT_MAPPER.readerForMapOf(Object.class).readValue(json);
  }
}
