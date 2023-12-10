package com.mycompany.myapp.wire.logstash.infrastructure.secondary;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.logging.logstash.tcp")
public class LogstashTcpProperties {

  /**
   * Enable or disable Logstash
   */
  private boolean enabled;

  /**
   * Host name
   */
  private String host;

  /**
   * Port number
   */
  private int port;

  /**
   * Sets the size of the RingBuffer. Must be a positive power of 2.
   */
  private Integer ringBufferSize;

  /**
   * How long to wait for in-flight events during shutdown.
   */
  private Duration shutdownGracePeriod;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public Integer getRingBufferSize() {
    return ringBufferSize;
  }

  public void setRingBufferSize(Integer ringBufferSize) {
    this.ringBufferSize = ringBufferSize;
  }

  public Duration getShutdownGracePeriod() {
    return shutdownGracePeriod;
  }

  public void setShutdownGracePeriod(Duration shutdownGracePeriod) {
    this.shutdownGracePeriod = shutdownGracePeriod;
  }
}
