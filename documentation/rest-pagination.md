# Rest pagination

Let's consider a very common use case to get paginated information. In `BeersApplicationService` we have:

```java
public JhipsterSampleApplicationPage<Beer> list(JhipsterSampleApplicationPageable pagination) {
  // ...
}
```

To call that and expose a result using a rest service we can do something like that: 

```java
private final BeersApplicationService beers;

// ...

ResponseEntity<RestJhipsterSampleApplicationPage<RestBeer>> list(@Validated RestJhipsterSampleApplicationPageable pagination) {
  return ResponseEntity.ok(RestJhipsterSampleApplicationPage.from(beers.list(pagination.toPageable()), RestBeer::from))
}
```

And we'll need a mapping method in `RestBeer`: 

```java
static RestBeer from(Beer beer) {
  // ...
}
```