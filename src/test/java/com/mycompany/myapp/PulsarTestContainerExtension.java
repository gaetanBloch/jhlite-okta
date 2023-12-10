package com.mycompany.myapp;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PulsarContainer;
import org.testcontainers.utility.DockerImageName;

public class PulsarTestContainerExtension implements BeforeAllCallback {

  private final AtomicBoolean pulsarContainerStarted = new AtomicBoolean(false);

  @Override
  public void beforeAll(final ExtensionContext context) {
    if (!pulsarContainerStarted.get()) {
      final PulsarContainer pulsarContainer = new PulsarContainer(DockerImageName.parse("apachepulsar/pulsar:3.1.1"));
      pulsarContainer.start();
      pulsarContainerStarted.set(true);
      System.setProperty("pulsar.client.serviceUrl", pulsarContainer.getPulsarBrokerUrl());
    }
  }
}
