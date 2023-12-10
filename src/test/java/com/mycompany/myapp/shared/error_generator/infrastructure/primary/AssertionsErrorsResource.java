package com.mycompany.myapp.shared.error_generator.infrastructure.primary;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mycompany.myapp.shared.error.domain.Assert;
import com.mycompany.myapp.shared.error_generator.domain.NullElementInCollectionExceptionFactory;

@RestController
@RequestMapping("/api/assertion-errors")
class AssertionsErrorsResource {

  @GetMapping("missing-primary-mandatory-value")
  void missingPrimaryMandatoryValue() {
    Assert.notNull("myField", null);
  }

  @GetMapping("domain-null-element-in-collection")
  void domainNullElementInCollection() {
    throw NullElementInCollectionExceptionFactory.nullElementInCollection();
  }

  @GetMapping("number-value-too-low")
  void numberValueTooLow() {
    Assert.field("myField", 42).min(1337);
  }

  @GetMapping("number-value-too-high")
  void numberValueTooHigh() {
    Assert.field("myField", 1337).max(42);
  }

  @GetMapping("string-too-long")
  void stringTooLong() {
    Assert.field("myField", "a".repeat(42)).maxLength(10);
  }

  @GetMapping("string-too-short")
  void stringTooShort() {
    Assert.field("myField", "a".repeat(10)).minLength(42);
  }

  @GetMapping("too-many-elements")
  void tooManyElements() {
    Assert.field("myField", List.of("a", "b")).maxSize(1);
  }

  @GetMapping("null-element-in-collection")
  void nullElementInCollection() {
    Assert.field("myField", Arrays.asList("dummy", null)).noNullElement();
  }

  @GetMapping("not-after-time")
  void notAfterTime() {
    Assert.field("myField", Instant.ofEpochSecond(13)).after(Instant.ofEpochSecond(1337));
  }

  @GetMapping("not-before-time")
  void notBeforeTime() {
    Assert.field("myField", Instant.ofEpochSecond(1337)).before(Instant.ofEpochSecond(13));
  }
}
