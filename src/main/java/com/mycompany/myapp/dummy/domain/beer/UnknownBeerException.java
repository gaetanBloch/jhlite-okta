package com.mycompany.myapp.dummy.domain.beer;

import com.mycompany.myapp.dummy.domain.BeerId;

class UnknownBeerException extends RuntimeException {

  public UnknownBeerException(BeerId id) {
    super("Beer " + id.get() + " is unknown");
  }
}
