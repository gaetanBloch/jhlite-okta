# JHipster Sample Application

## Prerequisites

### Java

You need to have Java 21:
- [JDK 21](https://openjdk.java.net/projects/jdk/21/)

### Node.js and NPM

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js](https://nodejs.org/): We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

## Local environment

- [Local server](http://localhost:8080)
- [Local API doc](http://localhost:8080/swagger-ui.html)

<!-- jhipster-needle-localEnvironment -->

## Start up

```bash
./mvnw
```

```bash
docker compose -f src/main/docker/sonar.yml up -d
./mvnw clean verify sonar:sonar
```

```bash
docker compose -f src/main/docker/mongodb.yml up -d
```

```bash
docker compose -f src/main/docker/postgresql.yml up -d
```

```bash
docker compose -f src/main/docker/redis.yml up -d
```

```bash
docker compose -f src/main/docker/kafka.yml up -d
```

```bash
docker compose -f src/main/docker/pulsar.yml up -d
```

```bash
docker compose -f src/main/docker/cassandra.yml up -d
```

```bash
docker compose -f src/main/docker/cassandra-migration.yml up -d
```

```bash
docker compose -f src/main/docker/akhq.yml up -d
```

```bash
docker compose -f src/main/docker/consul.yml up -d
```

```bash
docker compose -f src/main/docker/keycloak.yml up -d
```


<!-- jhipster-needle-startupCommand -->

## Documentation

- [Hexagonal architecture](documentation/hexagonal-architecture.md)
- [Package types](documentation/package-types.md)
- [Assertions](documentation/assertions.md)
- [sonar](documentation/sonar.md)
- [Mongo DB](documentation/mongo-db.md)
- [Postgresql](documentation/postgresql.md)
- [Redis](documentation/redis.md)
- [Dev tools](documentation/dev-tools.md)
- [Apache Kafka](documentation/apache-kafka.md)
- [Cassandra](documentation/cassandra.md)
- [Caffeine](documentation/caffeine.md)
- [Cassandra Migration](documentation/cassandra-migration.md)
- [Application errors](documentation/application-errors.md)
- [Jpa pages](documentation/jpa-pages.md)
- [Logs Spy](documentation/logs-spy.md)
- [Mongock](documentation/mongock.md)
- [CORS configuration](documentation/cors-configuration.md)
- [Cucumber](documentation/cucumber.md)
- [Kipe authorization](documentation/kipe-authorization.md)

<!-- jhipster-needle-documentation -->
