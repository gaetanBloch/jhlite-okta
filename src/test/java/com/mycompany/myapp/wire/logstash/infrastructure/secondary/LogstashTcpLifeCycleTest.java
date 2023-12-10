package com.mycompany.myapp.wire.logstash.infrastructure.secondary;

import static com.mycompany.myapp.wire.logstash.infrastructure.secondary.LogstashTcpLifeCycle.ASYNC_LOGSTASH_APPENDER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.UnitTest;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

@UnitTest
@JsonTest
class LogstashTcpLifeCycleTest {

  @Autowired
  private JacksonTester<Map<String, String>> json;

  private final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

  @AfterEach
  void detachAppenders() {
    context.getLogger(Logger.ROOT_LOGGER_NAME).detachAndStopAllAppenders();
  }

  @Test
  void shouldStartWithMinimumConfig() {
    LogstashTcpProperties properties = new LogstashTcpProperties();
    properties.setHost("localhost");
    properties.setPort(5000);

    LogstashTcpLifeCycle logstashTcpLifeCycle = new LogstashTcpLifeCycle(null, properties);

    assertThat(logstashTcpLifeCycle.isRunning()).isFalse();
    assertThat(context.getLogger(Logger.ROOT_LOGGER_NAME).getAppender(ASYNC_LOGSTASH_APPENDER_NAME)).isNull();

    logstashTcpLifeCycle.start();

    assertThat(logstashTcpLifeCycle.isRunning()).isTrue();
    assertThat(context.getLogger(Logger.ROOT_LOGGER_NAME).getAppender(ASYNC_LOGSTASH_APPENDER_NAME)).isNotNull();
    assertThat(context.getLogger(Logger.ROOT_LOGGER_NAME).getAppender(ASYNC_LOGSTASH_APPENDER_NAME).isStarted()).isTrue();
  }

  @Test
  void shouldSetProperties() {
    LogstashTcpProperties properties = new LogstashTcpProperties();
    properties.setHost("127.0.0.1");
    properties.setPort(50000);
    properties.setRingBufferSize(4096);
    properties.setShutdownGracePeriod(Duration.ofMillis(100));

    LogstashTcpLifeCycle logstashTcpLifeCycle = new LogstashTcpLifeCycle(null, properties, new ObjectMapper());

    LogstashTcpSocketAppender logstashTcpSocketAppender = logstashTcpLifeCycle.logstashTcpSocketAppender(context);

    assertThat(logstashTcpSocketAppender.getDestinations()).contains(new InetSocketAddress("127.0.0.1", 50000));
    assertThat(logstashTcpSocketAppender.getContext()).isEqualTo(context);
    assertThat(logstashTcpSocketAppender.getRingBufferSize()).isEqualTo(4096);
    assertThat(logstashTcpSocketAppender.getShutdownGracePeriod().getMilliseconds()).isEqualTo(100);
  }

  @Test
  void shouldShortenedThrowableConverter() {
    LogstashTcpLifeCycle logstashTcpLifeCycle = new LogstashTcpLifeCycle(null, new LogstashTcpProperties(), new ObjectMapper());
    ThrowableHandlingConverter throwableConverter = logstashTcpLifeCycle.throwableConverter();
    assertThat(throwableConverter).isInstanceOf(ShortenedThrowableConverter.class);
  }

  @Test
  void shouldNotStop() {
    LogstashTcpProperties properties = new LogstashTcpProperties();
    properties.setHost("localhost");
    properties.setPort(5000);

    LogstashTcpLifeCycle logstashTcpLifeCycle = new LogstashTcpLifeCycle(null, properties, new ObjectMapper());

    logstashTcpLifeCycle.start();

    logstashTcpLifeCycle.stop();

    assertThat(logstashTcpLifeCycle.isRunning()).isTrue();
    assertThat(context.getLogger(Logger.ROOT_LOGGER_NAME).getAppender(ASYNC_LOGSTASH_APPENDER_NAME)).isNotNull();
    assertThat(context.getLogger(Logger.ROOT_LOGGER_NAME).getAppender(ASYNC_LOGSTASH_APPENDER_NAME).isStarted()).isTrue();
  }

  @Test
  void shouldSerializeFields() throws IOException {
    Map<String, String> fields = Map.of("app_name", "jhipster-lite", "app_port", "8080");
    LogstashTcpLifeCycle logstashTcpLifeCycle = new LogstashTcpLifeCycle(fields, new LogstashTcpProperties());

    ObjectContent<Map<String, String>> parsed = json.parse(logstashTcpLifeCycle.serializedFields());
    assertThat(parsed).hasFieldOrPropertyWithValue("app_name", "jhipster-lite").hasFieldOrPropertyWithValue("app_port", "8080");
  }

  @Test
  void shouldThrowIllegalStateException() {
    Map<String, String> map = new HashMap<>();
    map.put(null, "test");

    LogstashTcpLifeCycle logstashTcpLifeCycle = new LogstashTcpLifeCycle(map, new LogstashTcpProperties(), new ObjectMapper());

    assertThatThrownBy(logstashTcpLifeCycle::serializedFields).isInstanceOf(IllegalStateException.class);
  }
}
