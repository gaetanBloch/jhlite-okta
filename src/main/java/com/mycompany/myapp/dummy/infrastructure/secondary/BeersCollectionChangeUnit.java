package com.mycompany.myapp.dummy.infrastructure.secondary;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.index.TextIndexDefinition.TextIndexDefinitionBuilder;

import com.mycompany.myapp.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ChangeUnit(id = "create-beers-collection", order = "002", author = "jhipster")
@ExcludeFromGeneratedCodeCoverage(reason = "Rollback not called in a normal lifecycle and an implementation detail")
public class BeersCollectionChangeUnit {

  @Execution
  public void createCollection(MongoTemplate mongo) {
    mongo.createCollection(BeerDocument.class);

    TextIndexDefinition indexDefinition = new TextIndexDefinitionBuilder().onField("selling_state").build();
    mongo.indexOps(BeerDocument.class).ensureIndex(indexDefinition);
  }

  @RollbackExecution
  public void dropCollection(MongoTemplate mongo) {
    mongo.dropCollection(BeerDocument.class);
  }
}
