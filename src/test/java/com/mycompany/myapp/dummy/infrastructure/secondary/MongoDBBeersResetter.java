package com.mycompany.myapp.dummy.infrastructure.secondary;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoDBBeersResetter {

  @Autowired
  private MongoDBBeersRepository beers;

  @Before
  public void resetBeers() {
    beers.deleteAll();
  }
}
