package com.mycompany.myapp.shared.useridentity.domain;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.mycompany.myapp.shared.error.domain.Assert;

public record Email(String email) {
  public Email {
    Assert.field("email", email).notBlank().maxLength(255);
  }

  public static Optional<Email> of(String email) {
    return Optional.ofNullable(email).filter(StringUtils::isNotBlank).map(Email::new);
  }

  public String get() {
    return email();
  }
}
