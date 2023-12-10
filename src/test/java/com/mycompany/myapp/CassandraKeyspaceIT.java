package com.mycompany.myapp;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.Metadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class CassandraKeyspaceIT {

  private static final String KEYSPACE = "jhipsterSampleApplication";

  @Autowired
  private CqlSession session;

  @Test
  void shouldListCassandraUnitKeyspace() {
    Metadata metadata = session.getMetadata();
    assertThat(metadata.getKeyspace(KEYSPACE)).isNotEmpty();
  }
}
