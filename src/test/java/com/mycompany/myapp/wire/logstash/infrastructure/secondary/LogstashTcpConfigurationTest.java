package com.mycompany.myapp.wire.logstash.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.UnitTest;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

@UnitTest
@JsonTest
class LogstashTcpConfigurationTest {

  @Autowired
  private JacksonTester<Map<String, String>> json;

  @Test
  void shouldAddAppNameOnly() throws IOException {
    LogstashTcpConfiguration c = new LogstashTcpConfiguration("jhipster-lite", null, new LogstashTcpProperties());

    ObjectContent<Map<String, String>> parsed = json.parse(c.logstashTcpLifeCycle().serializedFields());
    assertThat(parsed.getObject()).hasSize(1);
  }
}
