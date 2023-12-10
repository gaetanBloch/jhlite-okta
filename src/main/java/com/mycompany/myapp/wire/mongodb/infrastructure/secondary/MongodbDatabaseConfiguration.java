package com.mycompany.myapp.wire.mongodb.infrastructure.secondary;

import com.mycompany.myapp.wire.mongodb.infrastructure.secondary.JSR310DateConverters.DateToZonedDateTimeConverter;
import com.mycompany.myapp.wire.mongodb.infrastructure.secondary.JSR310DateConverters.ZonedDateTimeToDateConverter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories({ "com.mycompany.myapp" })
@Import(value = MongoAutoConfiguration.class)
class MongodbDatabaseConfiguration {

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(DateToZonedDateTimeConverter.INSTANCE);
    converters.add(ZonedDateTimeToDateConverter.INSTANCE);
    return new MongoCustomConversions(converters);
  }
}
