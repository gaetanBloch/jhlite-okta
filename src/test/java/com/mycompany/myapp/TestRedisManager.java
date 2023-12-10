package com.mycompany.myapp;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

class TestRedisManager implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  private static GenericContainer redisContainer;

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    if (redisContainer != null) {
      return;
    }

    redisContainer = new GenericContainer(DockerImageName.parse("redis:7.2.3")).withExposedPorts(6379);;

    redisContainer.start();

    System.setProperty("TEST_REDIS_URL", "redis://" + redisContainer.getHost() + ":" + redisContainer.getMappedPort(6379));
    Runtime.getRuntime()
        .addShutdownHook(new Thread(stopContainer()));
  }

  private Runnable stopContainer() {
    return redisContainer::stop;
  }
}
