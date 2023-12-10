package com.mycompany.myapp.wire.cache.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;

import javax.cache.Cache;
import javax.cache.CacheManager;
import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.config.ResourceType;
import org.ehcache.jsr107.Eh107Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.mycompany.myapp.IntegrationTest;

@IntegrationTest(properties = { "application.cache.ehcache.max-entries=1000", "application.cache.ehcache.time-to-live-seconds=86400" })
class CacheConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Autowired
  CacheConfiguration cacheConfiguration;

  @Autowired
  CacheManager cm;

  @Test
  void shouldApplyProperties() {
    cacheConfiguration.createCache(cm, "shouldApplyProperties");

    Cache<Object, Object> cache = cm.getCache("shouldApplyProperties");

    @SuppressWarnings("unchecked")
    CacheRuntimeConfiguration<Object, Object> config = (CacheRuntimeConfiguration<Object, Object>) cache
      .getConfiguration(Eh107Configuration.class)
      .unwrap(CacheRuntimeConfiguration.class);

    assertThat(config.getResourcePools().getPoolForResource(ResourceType.Core.HEAP).getSize()).isEqualTo(1000);

    assertThat(config.getExpiryPolicy().getExpiryForCreation(42L, "entry").getSeconds()).isEqualTo(86400);
  }
}
