package com.mycompany.myapp.dummy.domain.order;

import com.mycompany.myapp.dummy.domain.Amount;
import com.mycompany.myapp.dummy.domain.BeerId;
import com.mycompany.myapp.shared.error.domain.Assert;

public record OrderedBeer(BeerId beer, Amount unitPrice) {
  public OrderedBeer {
    Assert.notNull("beer", beer);
    Assert.notNull("unitPrice", unitPrice);
  }
}
