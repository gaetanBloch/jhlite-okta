package com.mycompany.myapp.shared.error_generator.domain;

import com.mycompany.myapp.shared.error.domain.NullElementInCollectionException;

public final class NullElementInCollectionExceptionFactory {

  private NullElementInCollectionExceptionFactory() {}

  public static NullElementInCollectionException nullElementInCollection() {
    return new NullElementInCollectionException("field");
  }
}
