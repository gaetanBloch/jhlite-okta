package com.mycompany.myapp.wire.redis.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.wire.redis.infrastructure.secondary.JSR310DateConverters.*;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@UnitTest
class JSR310DateConvertersTest {

  @Test
  @DisplayName("Should convert ZonedDateTime to Date")
  void convert_WhenZoneDateTime_ThenConvertToDate() {
    ZonedDateTime source = ZonedDateTime.parse("2022-02-15T12:00:00+01:00[Europe/Paris]");
    Date expected = Date.from(source.toInstant());
    Date result = ZonedDateTimeToDateConverter.INSTANCE.convert(source);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should convert Date to ZonedDateTime")
  void convert_WhenDate_ThenConvertToZoneDateTime() {
    ZonedDateTime expected = ZonedDateTime.parse("2022-02-15T12:00:00+01:00[Europe/Paris]");
    Date source = Date.from(expected.toInstant());
    ZonedDateTime result = DateToZonedDateTimeConverter.INSTANCE.convert(source);
    assertThat(result).isEqualTo(expected);
  }
}
