package com.mycompany.myapp.wire.cassandra.infrastructure.secondary;

import com.mycompany.myapp.wire.cassandra.infrastructure.secondary.CassandraJSR310DateConverters.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
class CassandraDatabaseConfiguration {

  @Bean
  CassandraCustomConversions cassandraCustomConversions() {
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(TupleToZonedDateTimeConverter.INSTANCE);
    converters.add(ZonedDateTimeToTupleConverter.INSTANCE);
    return new CassandraCustomConversions(converters);
  }
}
