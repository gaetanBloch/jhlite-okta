package com.mycompany.myapp.wire.redis.infrastructure.secondary;

import com.mycompany.myapp.wire.redis.infrastructure.secondary.JSR310DateConverters.DateToZonedDateTimeConverter;
import com.mycompany.myapp.wire.redis.infrastructure.secondary.JSR310DateConverters.ZonedDateTimeToDateConverter;
import java.util.List;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories({ "com.mycompany.myapp" })
@Import(value = RedisAutoConfiguration.class)
class RedisDatabaseConfiguration {

  @Bean
  public RedisCustomConversions redisCustomConversions() {
    return new RedisCustomConversions(List.of(DateToZonedDateTimeConverter.INSTANCE, ZonedDateTimeToDateConverter.INSTANCE));
  }
}
