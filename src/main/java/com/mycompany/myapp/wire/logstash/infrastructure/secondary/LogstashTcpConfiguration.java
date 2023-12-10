package com.mycompany.myapp.wire.logstash.infrastructure.secondary;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Logstash TCP appender
 */
@Configuration
@ConditionalOnProperty(name = "application.logging.logstash.tcp.enabled", havingValue = "true")
public class LogstashTcpConfiguration {

  public static final String FIELD_APP_NAME = "app_name";
  public static final String FIELD_APP_PORT = "app_port";

  private final String appName;
  private final String appPort;
  private final LogstashTcpProperties properties;

  public LogstashTcpConfiguration(
    @Value("${spring.application.name}") String appName,
    @Value("${server.port:#{null}}") String appPort,
    LogstashTcpProperties properties
  ) {
    this.appName = appName;
    this.appPort = appPort;
    this.properties = properties;
  }

  @Bean
  public LogstashTcpLifeCycle logstashTcpLifeCycle() {
    return new LogstashTcpLifeCycle(customFields(appName, appPort), properties);
  }

  private Map<String, String> customFields(String appName, String appPort) {
    Map<String, String> map = new HashMap<>();
    map.put(FIELD_APP_NAME, appName);
    if (appPort != null) {
      map.put(FIELD_APP_PORT, appPort);
    }
    return map;
  }
}
