package com.mycompany.myapp.dummy.infrastructure.secondary;

import com.mycompany.myapp.dummy.domain.beer.BeerSellingState;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MongoDBBeersRepository extends CrudRepository<BeerDocument, UUID> {
  Collection<BeerDocument> findBySellingState(BeerSellingState sold);
}
