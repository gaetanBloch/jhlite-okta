package com.mycompany.myapp;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.testcontainers.containers.CassandraContainer;

import java.net.InetAddress;
import java.net.UnknownHostException;

class TestCassandraManager implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered  {

  private static final String KEYSPACE = "jhipsterSampleApplication";
  private static CassandraContainer<?> cassandraContainer;

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    if (cassandraContainer != null) {
      return;
    }
    createCassandraContainer();
    cassandraContainer.start();

    try (CqlSession session = getCqlSession()) {
      createTestKeyspace(session);
    }
    registerEnvironmentVariables();

    SpringApplication.getShutdownHandlers()
      .add(new Thread(stopContainer()));
  }

  private void registerEnvironmentVariables() {
    System.setProperty("TEST_CASSANDRA_PORT", String.valueOf(cassandraContainer.getMappedPort(CassandraContainer.CQL_PORT)));
    System.setProperty("TEST_CASSANDRA_CONTACT_POINT", getContainerIpAddress());
    System.setProperty("TEST_CASSANDRA_DC", cassandraContainer.getLocalDatacenter());
    System.setProperty("TEST_CASSANDRA_KEYSPACE", KEYSPACE);
  }

  /**
   *  For the driver contact point, an IP address should be used, and not "localhost" which can resolve to 2 addresses: ipv4 and ipv6.
   *  If it happens, the driver creates 2 contact points, and the second one throws a WARN in logs.
   *  Currently, cassandraContainer.getHost() returns "localhost", so it can't be used directly.
   * @see <a href="https://docs.datastax.com/en/developer/java-driver/latest/manual/core/configuration/reference/">basic.contact-points comments</a>
   * @return cassandra container's ip address, or 127.0.0.1 as fallback
   */
  private String getContainerIpAddress() {
    try {
      return InetAddress.getByName(cassandraContainer.getHost()).getHostAddress();
    } catch(UnknownHostException e) {
      return "127.0.0.1";
    }
  }

  private void createTestKeyspace(CqlSession session) {
    String createQuery = "CREATE KEYSPACE " + KEYSPACE + " WITH replication={'class' : 'SimpleStrategy', 'replication_factor':1}";
    session.execute(createQuery);
  }

  private CqlSession getCqlSession() {
    return CqlSession
      .builder()
      .addContactPoint(cassandraContainer.getContactPoint())
      .withLocalDatacenter(cassandraContainer.getLocalDatacenter())
      .build();
  }

  private void createCassandraContainer() {
    cassandraContainer = new CassandraContainer<>("cassandra:4.1.3");
  }

  private Runnable stopContainer() {
    return cassandraContainer::stop;
  }
}
