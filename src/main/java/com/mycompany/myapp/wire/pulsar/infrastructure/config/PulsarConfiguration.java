package com.mycompany.myapp.wire.pulsar.infrastructure.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConfiguration {

  @Bean
  PulsarClient pulsarClient(PulsarProperties properties) throws PulsarClientException {
    return PulsarClient.builder().loadConf(properties.getClientProps()).build();
  }
}
