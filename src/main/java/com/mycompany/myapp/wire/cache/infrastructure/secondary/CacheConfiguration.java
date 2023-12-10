package com.mycompany.myapp.wire.cache.infrastructure.secondary;

import java.time.Duration;
import javax.cache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
class CacheConfiguration {

  private final javax.cache.configuration.Configuration<Object, Object> configuration;

  public CacheConfiguration(EhcacheProperties properties) {
    this.configuration =
      Eh107Configuration.fromEhcacheCacheConfiguration(
        CacheConfigurationBuilder
          .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(properties.getMaxEntries()))
          .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(properties.getTimeToLiveSeconds())))
          .build()
      );
  }

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> {
      // jhipster-needle-jcache-add-entity
    };
  }

  /**
   * Create or replace a cache with the default configuration
   *
   * @param cm        cache manager
   * @param cacheName cache name
   */
  void createCache(CacheManager cm, String cacheName) {
    createCache(cm, cacheName, configuration);
  }

  /**
   * Create or replace a cache
   *
   * @param cm            cache manager
   * @param cacheName     cache name
   * @param configuration cache configuration
   */
  static void createCache(
    javax.cache.CacheManager cm,
    String cacheName,
    javax.cache.configuration.Configuration<Object, Object> configuration
  ) {
    javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
    if (cache != null) {
      cache.clear();
    } else {
      cm.createCache(cacheName, configuration);
    }
  }
}
