package com.mycompany.myapp.wire.kafka.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import com.mycompany.myapp.UnitTest;

@UnitTest
class KafkaPropertiesTest {

  private KafkaProperties kafkaProperties;

  @BeforeEach
  public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    kafkaProperties = new KafkaProperties();
    ReflectionTestUtils.setField(kafkaProperties, "bootstrapServers", "localhost:9092");
    ReflectionTestUtils.setField(kafkaProperties, "pollingTimeout", 10_000);
    Method postConstruct = KafkaProperties.class.getDeclaredMethod("init");
    postConstruct.setAccessible(true);
    postConstruct.invoke(kafkaProperties);
  }

  @Test
  void shouldGetConsumer() {
    assertThat(kafkaProperties.getConsumer()).containsEntry(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
  }

  @Test
  void shouldSetConsumer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Map<String, Object> consumer = new HashMap<>();
    consumer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    Method postConstruct = KafkaProperties.class.getDeclaredMethod("init");
    postConstruct.setAccessible(true);
    postConstruct.invoke(kafkaProperties);
    kafkaProperties.setConsumer(consumer);

    assertThat(kafkaProperties.getConsumer()).containsEntry(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
  }

  @Test
  void shouldGetProducer() {
    assertThat(kafkaProperties.getConsumer()).containsEntry(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
  }

  @Test
  void shouldSetProducer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Map<String, Object> producer = new HashMap<>();
    producer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    Method postConstruct = KafkaProperties.class.getDeclaredMethod("init");
    postConstruct.setAccessible(true);
    postConstruct.invoke(kafkaProperties);
    kafkaProperties.setProducer(producer);

    assertThat(kafkaProperties.getConsumer()).containsEntry(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
  }

  @Test
  void shouldGetPollingTimeout() {
    assertThat(kafkaProperties.getPollingTimeout()).isEqualTo(10_000);
  }

  @Test
  void shouldSetPollingTimeout() {
    kafkaProperties.setPollingTimeout(20_000);

    assertThat(kafkaProperties.getPollingTimeout()).isEqualTo(20_000);
  }
}
