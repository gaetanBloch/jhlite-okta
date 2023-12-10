package com.mycompany.myapp.shared.pagination.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.NumberValueTooHighException;
import com.mycompany.myapp.shared.error.domain.NumberValueTooLowException;

@UnitTest
class JhipsterSampleApplicationPageableTest {

  @Test
  void shouldNotBuildWithNegativePage() {
    assertThatThrownBy(() -> new JhipsterSampleApplicationPageable(-1, 10))
      .isExactlyInstanceOf(NumberValueTooLowException.class)
      .hasMessageContaining("page");
  }

  @Test
  void shouldNotBuildWithPageSizeAtZero() {
    assertThatThrownBy(() -> new JhipsterSampleApplicationPageable(0, 0))
      .isExactlyInstanceOf(NumberValueTooLowException.class)
      .hasMessageContaining("pageSize");
  }

  @Test
  void shouldNotBuildWithPageSizeOverHundred() {
    assertThatThrownBy(() -> new JhipsterSampleApplicationPageable(0, 101))
      .isExactlyInstanceOf(NumberValueTooHighException.class)
      .hasMessageContaining("pageSize");
  }

  @Test
  void shouldGetFirstPageInformation() {
    JhipsterSampleApplicationPageable pageable = new JhipsterSampleApplicationPageable(0, 15);

    assertThat(pageable.page()).isZero();
    assertThat(pageable.pageSize()).isEqualTo(15);
    assertThat(pageable.offset()).isZero();
  }

  @Test
  void shouldGetPageableInformation() {
    JhipsterSampleApplicationPageable pageable = new JhipsterSampleApplicationPageable(2, 15);

    assertThat(pageable.page()).isEqualTo(2);
    assertThat(pageable.pageSize()).isEqualTo(15);
    assertThat(pageable.offset()).isEqualTo(30);
  }
}
