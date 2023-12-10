package com.mycompany.myapp.wire.logstash.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.encoder.Encoder;
import java.net.InetSocketAddress;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.mycompany.myapp.IntegrationTest;

@IntegrationTest
class LogstashTcpConfigurationIT {

  @Nested
  @IntegrationTest(
    properties = {
      "spring.application.name=lite",
      "server.port=8080",
      "application.logging.logstash.tcp.enabled=true",
      "application.logging.logstash.tcp.host=127.0.0.1",
      "application.logging.logstash.tcp.port=50000",
      "application.logging.logstash.tcp.ring_buffer_size=4096",
      "application.logging.logstash.tcp.shutdown_grace_period=PT1S",
    }
  )
  class Enabled {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void shouldEnableLogstash() {
      LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

      Appender<ILoggingEvent> appender = context
        .getLogger(Logger.ROOT_LOGGER_NAME)
        .getAppender(LogstashTcpLifeCycle.ASYNC_LOGSTASH_APPENDER_NAME);

      assertThat(appender).isInstanceOf(LogstashTcpSocketAppender.class);

      LogstashTcpSocketAppender logstashTcpSocketAppender = (LogstashTcpSocketAppender) appender;

      assertThat(logstashTcpSocketAppender.getDestinations()).contains(new InetSocketAddress("127.0.0.1", 50000));
      assertThat(logstashTcpSocketAppender.getRingBufferSize()).isEqualTo(4096);
      assertThat(logstashTcpSocketAppender.getShutdownGracePeriod().getMilliseconds()).isEqualTo(1000);

      Encoder<ILoggingEvent> encoder = logstashTcpSocketAppender.getEncoder();

      assertThat(encoder).isInstanceOf(LogstashEncoder.class);

      LogstashEncoder logstashEncoder = (LogstashEncoder) encoder;

      assertThat(logstashEncoder.getCustomFields()).contains("lite").contains("8080");

      assertThat(applicationContext.getBean("logstashTcpLifeCycle")).isInstanceOf(LogstashTcpLifeCycle.class);
    }
  }

  @Nested
  @IntegrationTest(properties = { "application.logging.logstash.tcp.enabled=false" })
  class Disabled {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void shouldDisableLogstash() {
      LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

      Appender<ILoggingEvent> appender = context
        .getLogger(Logger.ROOT_LOGGER_NAME)
        .getAppender(LogstashTcpLifeCycle.ASYNC_LOGSTASH_APPENDER_NAME);

      assertThat(appender).isNull();

      assertThatThrownBy(() -> applicationContext.getBean("logstashTcpLifeCycle")).isInstanceOf(NoSuchBeanDefinitionException.class);
    }
  }
}
