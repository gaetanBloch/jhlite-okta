package com.mycompany.myapp.wire.cache.infrastructure.secondary;

import static org.mockito.Mockito.*;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mycompany.myapp.UnitTest;

@UnitTest
@ExtendWith(MockitoExtension.class)
class CacheConfigurationTest {

  private static final CacheConfiguration cacheConfiguration = new CacheConfiguration(new EhcacheProperties());

  @Mock
  CacheManager cm;

  @Mock
  Cache<Object, Object> cache;

  @Mock
  Configuration<Object, Object> customConfig;

  @Test
  void shouldCreateDefaultCache() {
    cacheConfiguration.createCache(cm, "shouldCreateDefaultCache");
    verify(cm).createCache(eq("shouldCreateDefaultCache"), any());
  }

  @Test
  void shouldCreateCustomCache() {
    CacheConfiguration.createCache(cm, "shouldCreateCustomCache", customConfig);
    verify(cm).createCache("shouldCreateCustomCache", customConfig);
  }

  @Test
  void shouldClearExistingCache() {
    // given an existing cache "test"
    doReturn(cache).when(cm).getCache("shouldClearExistingCache");

    // when attempting to create a new cache "test"
    CacheConfiguration.createCache(cm, "shouldClearExistingCache", customConfig);

    // then destroy and create cache "test"
    verify(cache).clear();
    verify(cm, never()).createCache("shouldClearExistingCache", customConfig);
  }
}
