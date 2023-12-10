package com.mycompany.myapp.dummy.infrastructure.secondary;

import com.mycompany.myapp.dummy.domain.BeerId;
import com.mycompany.myapp.dummy.domain.beer.Beer;
import com.mycompany.myapp.dummy.domain.beer.BeerSellingState;
import com.mycompany.myapp.dummy.domain.beer.Beers;
import com.mycompany.myapp.dummy.domain.beer.BeersRepository;
import com.mycompany.myapp.shared.error.domain.Assert;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class SpringDataBeersRepository implements BeersRepository {

  private final MongoDBBeersRepository beers;

  public SpringDataBeersRepository(MongoDBBeersRepository beers) {
    this.beers = beers;
  }

  @Override
  public void save(Beer beer) {
    Assert.notNull("beer", beer);

    beers.save(BeerDocument.from(beer));
  }

  @Override
  public Beers catalog() {
    return new Beers(beers.findBySellingState(BeerSellingState.SOLD).stream().map(BeerDocument::toDomain).toList());
  }

  @Override
  public Optional<Beer> get(BeerId beer) {
    Assert.notNull("beer", beer);

    return beers.findById(beer.get()).map(BeerDocument::toDomain);
  }
}
