package com.mycompany.myapp.dummy.domain.beer;

import com.mycompany.myapp.dummy.domain.BeerId;
import com.mycompany.myapp.shared.error.domain.Assert;

public class BeersRemover {

  private final BeersRepository beers;

  public BeersRemover(BeersRepository beers) {
    Assert.notNull("beers", beers);

    this.beers = beers;
  }

  public void remove(BeerId id) {
    Assert.notNull("id", id);

    Beer beer = beers.get(id).orElseThrow(() -> new UnknownBeerException(id));

    beer.removeFromSell();
    beers.save(beer);
  }
}
