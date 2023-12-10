package com.mycompany.myapp.shared.error.infrastructure.primary;

import com.mycompany.myapp.shared.error.domain.JhipsterSampleApplicationException;

public final class JhipsterSampleApplicationExceptionFactory {

  private JhipsterSampleApplicationExceptionFactory() {}

  public static final JhipsterSampleApplicationException buildEmptyException() {
    return JhipsterSampleApplicationException.builder(null).build();
  }
}
