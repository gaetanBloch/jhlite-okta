package com.mycompany.myapp.shared.pagination.infrastructure.primary;

import static com.mycompany.myapp.BeanValidationAssertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPageable;

@UnitTest
class RestJhipsterSampleApplicationPageableTest {

  @Test
  void shouldConvertToDomain() {
    JhipsterSampleApplicationPageable pageable = pageable().toPageable();

    assertThat(pageable.page()).isEqualTo(1);
    assertThat(pageable.pageSize()).isEqualTo(15);
  }

  @Test
  void shouldNotValidateWithPageUnderZero() {
    RestJhipsterSampleApplicationPageable pageable = pageable();
    pageable.setPage(-1);

    assertThatBean(pageable).hasInvalidProperty("page");
  }

  @Test
  void shouldNotValidateWithSizeAtZero() {
    RestJhipsterSampleApplicationPageable pageable = pageable();
    pageable.setPageSize(0);

    assertThatBean(pageable).hasInvalidProperty("pageSize").withParameter("value", 1L);
  }

  @Test
  void shouldNotValidateWithPageSizeOverHundred() {
    RestJhipsterSampleApplicationPageable pageable = pageable();
    pageable.setPageSize(101);

    assertThatBean(pageable).hasInvalidProperty("pageSize");
  }

  private RestJhipsterSampleApplicationPageable pageable() {
    return new RestJhipsterSampleApplicationPageable(1, 15);
  }
}
