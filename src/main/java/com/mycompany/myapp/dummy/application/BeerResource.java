package com.mycompany.myapp.dummy.application;

import com.mycompany.myapp.shared.kipe.domain.Resource;

enum BeerResource implements Resource {
  BEERS("beers");

  private final String key;

  BeerResource(String key) {
    this.key = key;
  }

  @Override
  public String key() {
    return key;
  }
}
