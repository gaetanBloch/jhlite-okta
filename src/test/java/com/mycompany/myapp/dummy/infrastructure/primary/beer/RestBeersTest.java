package com.mycompany.myapp.dummy.infrastructure.primary.beer;

import static org.assertj.core.api.Assertions.*;

import com.mycompany.myapp.JsonHelper;
import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.dummy.domain.beer.Beers;
import com.mycompany.myapp.dummy.domain.beer.BeersFixture;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class RestBeersTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestBeers.from(new Beers(List.of(BeersFixture.beer()))))).isEqualTo(json());
  }

  private String json() {
    return "{\"beers\":[" + RestBeerTest.json() + "]}";
  }
}
