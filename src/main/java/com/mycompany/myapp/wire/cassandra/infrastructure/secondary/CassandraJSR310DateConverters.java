package com.mycompany.myapp.wire.cassandra.infrastructure.secondary;

import com.datastax.oss.driver.api.core.data.TupleValue;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.TupleType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class CassandraJSR310DateConverters {

  private CassandraJSR310DateConverters() {}

  @ReadingConverter
  public static class TupleToZonedDateTimeConverter implements Converter<TupleValue, ZonedDateTime> {

    public static final TupleToZonedDateTimeConverter INSTANCE = new TupleToZonedDateTimeConverter();

    private TupleToZonedDateTimeConverter() {
    }

    @Override
    public ZonedDateTime convert(TupleValue source) {
      Instant instant = source.getInstant(0);
      ZoneId zoneId = ZoneId.of(source.getString(1));
      return instant.atZone(zoneId);
    }
  }

  @WritingConverter
  public static class ZonedDateTimeToTupleConverter implements Converter<ZonedDateTime, TupleValue> {

    private final TupleType type = DataTypes.tupleOf(DataTypes.TIMESTAMP, DataTypes.TEXT);

    public static final ZonedDateTimeToTupleConverter INSTANCE = new ZonedDateTimeToTupleConverter();

    private ZonedDateTimeToTupleConverter() {}

    @Override
    public TupleValue convert(@Nonnull ZonedDateTime source) {
      TupleValue tupleValue = type.newValue();
      tupleValue.setInstant(0, source.toInstant());
      tupleValue.setString(1, source.getZone().toString());
      return tupleValue;
    }
  }
}
