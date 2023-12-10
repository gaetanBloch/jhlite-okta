package com.mycompany.myapp.wire.postgresql.infrastructure.secondary;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.mycompany.myapp" }, enableDefaultTransactions = false)
class DatabaseConfiguration {}
