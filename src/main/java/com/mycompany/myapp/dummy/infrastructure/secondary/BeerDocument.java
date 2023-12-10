package com.mycompany.myapp.dummy.infrastructure.secondary;

import com.mycompany.myapp.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import com.mycompany.myapp.dummy.domain.BeerId;
import com.mycompany.myapp.dummy.domain.beer.Beer;
import com.mycompany.myapp.dummy.domain.beer.BeerSellingState;
import com.mycompany.myapp.shared.error.domain.Assert;
import java.math.BigDecimal;
import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "beers")
class BeerDocument {

  @Id
  @Field(name = "id")
  private UUID id;

  @Field(name = "name")
  private String name;

  @Field(name = "unit_price")
  private BigDecimal unitPrice;

  @Field(name = "selling_state")
  private BeerSellingState sellingState;

  public static BeerDocument from(Beer beer) {
    Assert.notNull("beer", beer);

    return new BeerDocument()
      .id(beer.id().get())
      .name(beer.name().get())
      .unitPrice(beer.unitPrice().get())
      .sellingState(beer.sellingState());
  }

  private BeerDocument id(UUID id) {
    this.id = id;

    return this;
  }

  private BeerDocument name(String name) {
    this.name = name;

    return this;
  }

  private BeerDocument unitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;

    return this;
  }

  private BeerDocument sellingState(BeerSellingState sellingState) {
    this.sellingState = sellingState;

    return this;
  }

  public Beer toDomain() {
    return Beer.builder().id(new BeerId(id)).name(name).unitPrice(unitPrice).sellingState(sellingState).build();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(id).hashCode();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    BeerDocument other = (BeerDocument) obj;

    return new EqualsBuilder().append(id, other.id).isEquals();
  }
}
