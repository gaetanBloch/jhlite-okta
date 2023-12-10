package com.mycompany.myapp;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaTestContainerExtension implements BeforeAllCallback {

  protected static AtomicBoolean kafkaContainerStarted = new AtomicBoolean(false);
  public static KafkaContainer kafkaContainer;

  @Override
  public void beforeAll(final ExtensionContext context) {
    if (!kafkaContainerStarted.get()) {
      kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.1.1")).withNetwork(null);
      kafkaContainer.start();
      kafkaContainerStarted.set(true);
      System.setProperty("kafka.bootstrap.servers", kafkaContainer.getBootstrapServers());
    }
  }
}
