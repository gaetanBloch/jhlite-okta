package com.mycompany.myapp.wire.logstash.infrastructure.secondary;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import ch.qos.logback.core.util.Duration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

public class LogstashTcpLifeCycle implements SmartLifecycle {

  public static final String ASYNC_LOGSTASH_APPENDER_NAME = "ASYNC_LOGSTASH_TCP";

  private static final Logger log = LoggerFactory.getLogger(LogstashTcpLifeCycle.class);

  private final Map<String, String> fields;
  private final LogstashTcpProperties properties;
  private final ObjectMapper objectMapper;

  private boolean running;

  public LogstashTcpLifeCycle(Map<String, String> fields, LogstashTcpProperties properties) {
    this(fields, properties, null);
  }

  public LogstashTcpLifeCycle(Map<String, String> fields, LogstashTcpProperties properties, ObjectMapper objectMapper) {
    this.fields = fields;
    this.properties = properties;
    this.objectMapper = Optional.ofNullable(objectMapper).orElse(new ObjectMapper());
  }

  @Override
  @SuppressWarnings("java:S4792")
  public void start() {
    log.debug("Adding Logstash TCP appender to {}:{}", properties.getHost(), properties.getPort());

    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    LogstashTcpSocketAppender logstashTcpSocketAppender = logstashTcpSocketAppender(context);
    logstashTcpSocketAppender.start();
    context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(logstashTcpSocketAppender);
    running = true;
  }

  @Override
  public void stop() {
    // nothing to do, lifecycle is handled externally
  }

  @Override
  public boolean isRunning() {
    return running;
  }

  protected LogstashTcpSocketAppender logstashTcpSocketAppender(LoggerContext context) {
    // Documentation is available at: https://github.com/logstash/logstash-logback-encoder
    LogstashTcpSocketAppender logstashAppender = new LogstashTcpSocketAppender();
    logstashAppender.addDestinations(new InetSocketAddress(properties.getHost(), properties.getPort()));
    logstashAppender.setContext(context);
    logstashAppender.setEncoder(logstashEncoder());
    logstashAppender.setName(ASYNC_LOGSTASH_APPENDER_NAME);
    if (properties.getRingBufferSize() != null) {
      logstashAppender.setRingBufferSize(properties.getRingBufferSize());
    }
    if (properties.getShutdownGracePeriod() != null) {
      logstashAppender.setShutdownGracePeriod(Duration.buildByMilliseconds(properties.getShutdownGracePeriod().toMillis()));
    }
    return logstashAppender;
  }

  protected LogstashEncoder logstashEncoder() {
    final LogstashEncoder logstashEncoder = new LogstashEncoder();
    logstashEncoder.setThrowableConverter(throwableConverter());
    logstashEncoder.setCustomFields(serializedFields());
    return logstashEncoder;
  }

  protected ThrowableHandlingConverter throwableConverter() {
    final ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
    throwableConverter.setRootCauseFirst(true);
    return throwableConverter;
  }

  protected String serializedFields() {
    try {
      return objectMapper.writeValueAsString(fields);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Unable to serialize custom fields", e);
    }
  }
}
