package com.mycompany.myapp;

import com.datastax.oss.driver.api.core.CqlSession;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.FileCQLDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

class TestCassandraMigrationLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

  private static final Logger log = LoggerFactory.getLogger(TestCassandraMigrationLoader.class);
  private static final String cqlDir = "config/cql/changelog/";
  private static final String pattern = ".cql";
  private static boolean migrationDone = false;

  @Override
  public int getOrder() {
    return 1;
  }

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    if (migrationDone) return;

    try(CqlSession session = getCqlSession()) {
      loadMigrationScripts(session);
      migrationDone = true;
    }
  }

  public void loadMigrationScripts(CqlSession session) {
    CQLDataLoader dataLoader = new CQLDataLoader(session);

    URL dirUrl = ClassLoader.getSystemResource(cqlDir);
    if (dirUrl == null) { // protect for empty directory
      return;
    }

    try (Stream<Path> stream = Files.list(Paths.get(dirUrl.toURI()))){
      stream
        .map(Path::toString)
        .filter(file -> file.endsWith(pattern))
        .sorted()
        .map(file -> new FileCQLDataSet(file, false, false))
        .forEach(dataLoader::load);
    } catch (IOException e) {
      log.error("error trying to read CQL changelog", e);
    } catch (URISyntaxException e) {
      log.error("error trying to get CQL changelog uri", e);
    }
  }

  private CqlSession getCqlSession() {
    String ip = System.getProperty("TEST_CASSANDRA_CONTACT_POINT");
    String port = System.getProperty("TEST_CASSANDRA_PORT");
    String dc = System.getProperty("TEST_CASSANDRA_DC");

    return CqlSession
      .builder()
      .addContactPoint(new InetSocketAddress(ip, Integer.parseInt(port)))
      .withLocalDatacenter(dc)
      .build();
  }
}
