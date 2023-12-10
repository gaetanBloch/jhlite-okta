package com.mycompany.myapp.dummy.domain.beer;

import com.mycompany.myapp.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

public record Beers(Collection<Beer> beers) {
  private static final Comparator<Beer> BEER_NAME_COMPARATOR = Comparator.comparing(beer -> beer.name().get());

  public Beers(Collection<Beer> beers) {
    Assert.field("beers", beers).notNull().noNullElement();

    this.beers = beers.stream().sorted(BEER_NAME_COMPARATOR).toList();
  }

  public Collection<Beer> get() {
    return beers();
  }

  public Stream<Beer> stream() {
    return beers().stream();
  }
}
