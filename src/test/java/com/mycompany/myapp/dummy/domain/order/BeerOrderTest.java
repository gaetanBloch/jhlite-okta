package com.mycompany.myapp.dummy.domain.order;

import static com.mycompany.myapp.dummy.domain.order.BeerOrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.dummy.domain.Amount;
import com.mycompany.myapp.shared.error.domain.MissingMandatoryValueException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

@UnitTest
class BeerOrderTest {

  @Test
  void shouldNotBuildEmptyOrder() {
    assertThatThrownBy(() -> BeerOrder.builder().build()).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldGetConsolidatedOrderLines() {
    BeerOrder order = beerOrder();

    assertThat(order.lines())
      .containsExactlyInAnyOrder(new BeerOrderLine(orderedCloakOfFeather(), 2), new BeerOrderLine(orderedAnteMeridiem(), 3));
  }

  @Test
  void shouldGetOrderTotal() {
    assertThat(beerOrder().total()).isEqualTo(new Amount(new BigDecimal("33.44")));
  }
}
