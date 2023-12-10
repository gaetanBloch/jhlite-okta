package com.mycompany.myapp.shared.kipe.domain;

import com.mycompany.myapp.shared.error.domain.Assert;

public record Action(String action) {
  public Action {
    Assert.notBlank("action", action);
  }
  
  @Override
  public String toString() {
    return action();
  }
}
