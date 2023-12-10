package com.mycompany.myapp.dummy.domain.beer;

import com.mycompany.myapp.shared.error.domain.Assert;

public record BeerName(String name) {
  public BeerName {
    Assert.field("name", name).notBlank().maxLength(255);
  }

  public String get() {
    return name();
  }
}
