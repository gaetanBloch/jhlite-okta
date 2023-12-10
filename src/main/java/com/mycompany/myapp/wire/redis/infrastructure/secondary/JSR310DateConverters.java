package com.mycompany.myapp.wire.redis.infrastructure.secondary;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>JSR310DateConverters class.</p>
 */
public final class JSR310DateConverters {

  private JSR310DateConverters() {}

  @SuppressWarnings("java:S6548")
  public static class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {

    public static final ZonedDateTimeToDateConverter INSTANCE = new ZonedDateTimeToDateConverter();

    private ZonedDateTimeToDateConverter() {}

    @Override
    public Date convert(ZonedDateTime source) {
      return Date.from(source.toInstant());
    }
  }

  @SuppressWarnings("java:S6548")
  public static class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {

    public static final DateToZonedDateTimeConverter INSTANCE = new DateToZonedDateTimeConverter();

    private DateToZonedDateTimeConverter() {}

    @Override
    public ZonedDateTime convert(Date source) {
      return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
    }
  }
}
