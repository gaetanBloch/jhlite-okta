package com.mycompany.myapp.wire.logstash.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class LogstashTcpPropertiesTest {

  @Test
  void shouldDisableByDefault() {
    assertThat(new LogstashTcpProperties().isEnabled()).isFalse();
  }
}
