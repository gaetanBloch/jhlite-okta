package com.mycompany.myapp.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public class CucumberJpaReset {

  @Autowired
  private Collection<JpaRepository<?, ?>> repositories;

  @After
  @Before
  @Transactional
  public void wipeData() {
    repositories.forEach(JpaRepository::deleteAllInBatch);
  }
}
