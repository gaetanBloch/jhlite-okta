package com.mycompany.myapp.wire.cassandra.infrastructure.secondary;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.wire.cassandra.infrastructure.secondary.CassandraJSR310DateConverters.*;
import com.datastax.oss.driver.api.core.data.TupleValue;
import com.datastax.oss.driver.api.core.type.DataTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
class CassandraJSR310DateConvertersTest {

  @Test
  @DisplayName("Should convert ZonedDateTime to Tuple<Instant, ZoneId>")
  void shouldConvertZonedDateTimeToTuple() {
    ZonedDateTime source = ZonedDateTime.parse("2023-01-06T12:00:00+01:00[Europe/Paris]");
    Instant expectedInstant = source.toInstant();
    ZoneId expectedZoneId = source.getZone();
    TupleValue result = ZonedDateTimeToTupleConverter.INSTANCE.convert(source);
    assertThat(result.getInstant(0)).isEqualTo(expectedInstant);
    assertThat(ZoneId.of(result.getString(1))).isEqualTo(expectedZoneId);
  }

  @Test
  @DisplayName("Should convert Tuple<Instant, ZoneId> to ZonedDateTime")
  void shouldConvertTupleToZonedDateTime() {
    ZonedDateTime expected = ZonedDateTime.parse("2023-01-06T12:00:00+01:00[Europe/Paris]");
    TupleValue source = DataTypes.tupleOf(DataTypes.TIMESTAMP, DataTypes.TEXT).newValue();
    source.setInstant(0, expected.toInstant());
    source.setString(1, expected.getZone().toString());
    ZonedDateTime result = TupleToZonedDateTimeConverter.INSTANCE.convert(source);
    assertThat(result).isEqualTo(expected);
  }
}
