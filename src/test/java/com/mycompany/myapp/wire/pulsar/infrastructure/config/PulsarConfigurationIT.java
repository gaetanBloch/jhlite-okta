package com.mycompany.myapp.wire.pulsar.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.mycompany.myapp.IntegrationTest;

@IntegrationTest
class PulsarConfigurationIT {

  private static final String TEST_PAYLOAD = "test-payload";

  @Autowired
  PulsarProperties config;

  @Autowired
  PulsarClient pulsarClient;

  @Test
  void testPulsar() throws Exception {
    Map<String, Object> producerProps = config.getProducerProps();
    Map<String, Object> consumerProps = config.getConsumerProps();

    assertThat(config.getClientProps()).containsEntry("numIoThreads", 8);

    assertThat(producerProps).containsEntry("topicName", "test-topic");

    List<String> consumerTopicNames = (List<String>) consumerProps.get("topicNames");
    assertThat(consumerTopicNames).hasSize(1);
    assertThat(consumerTopicNames.get(0)).isEqualTo("test-topic");
    assertThat(consumerProps).containsEntry("subscriptionName", "test-subscription");

    Consumer<String> consumer = pulsarClient.newConsumer(Schema.STRING)
      .loadConf(consumerProps)
      .subscribe();

    Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
      .loadConf(producerProps)
      .create();

    producer.send(TEST_PAYLOAD);

    Message<String> message = consumer.receive(5, TimeUnit.SECONDS);
    assertThat(message.getValue()).isEqualTo(TEST_PAYLOAD);
  }
}
