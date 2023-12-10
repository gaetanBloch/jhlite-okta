# Caffeine cache

Let's take a simple use case:

We have this repository:

```java
package com.mycompany.myapp.user.domain;

public interface UsersRepository {
  Users list(Group group);
}
```

Allowing us to list users in a given group. To implement this we'll make a call to our IDP:

```java
@Repository
class MyApplicationUsersRepository implements UsersRepository {

  private final MyIDPUsersRepository users;

  public MyApplicationUsersRepository(MyIDPUsersRepository users) {
    this.users = users;
  }

  @Override
  public Users list(Group group) {
    Assert.notNull("group", group);

    return users.list(group);
  }
}
```

But, we'll soon find that calling this has a cost and that the users in groups are not updated that often so we want to configure a simple local cache:

```java
package com.mycompany.myapp.user.infrastructure.secondary;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
class UsersCacheConfiguration {

  @Bean
  CacheManager usersCacheManager() {
    CaffeineCacheManager manager = new CaffeineCacheManager("users");

    manager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES));

    return manager;
  }
}
```

And update our repository to cache users:

```java
//...

import org.springframework.cache.annotation.Cacheable;

@Repository
class MyApplicationUsersRepository implements UsersRepository {

  //...

  @Override
  @Cacheable(cacheNames =  "users-cache")
  public Users list(Group group) {
    //...
  }
}
```

That's it, we now have a 5mn cache for our users.
