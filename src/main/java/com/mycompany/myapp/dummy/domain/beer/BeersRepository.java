package com.mycompany.myapp.dummy.domain.beer;

import com.mycompany.myapp.dummy.domain.BeerId;
import java.util.Optional;

public interface BeersRepository {
  void save(Beer beer);

  Beers catalog();

  Optional<Beer> get(BeerId beer);
}
