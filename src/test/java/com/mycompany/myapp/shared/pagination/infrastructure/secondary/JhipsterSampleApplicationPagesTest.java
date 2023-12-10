package com.mycompany.myapp.shared.pagination.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.MissingMandatoryValueException;
import com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPage;
import com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPageable;

@UnitTest
class JhipsterSampleApplicationPagesTest {

  @Test
  void shouldNotBuildPageableFromNullJhipsterSampleApplicationPageable() {
    assertThatThrownBy(() -> JhipsterSampleApplicationPages.from(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("pagination");
  }

  @Test
  void shouldBuildPageableFromJhipsterSampleApplicationPageable() {
    Pageable pagination = JhipsterSampleApplicationPages.from(pagination());

    assertThat(pagination.getPageNumber()).isEqualTo(2);
    assertThat(pagination.getPageSize()).isEqualTo(15);
    assertThat(pagination.getSort()).isEqualTo(Sort.unsorted());
  }

  @Test
  void shouldNotBuildWithoutSort() {
    assertThatThrownBy(() -> JhipsterSampleApplicationPages.from(pagination(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("sort");
  }

  @Test
  void shouldBuildPageableFromJhipsterSampleApplicationPageableAndSort() {
    Pageable pagination = JhipsterSampleApplicationPages.from(pagination(), Sort.by("dummy"));

    assertThat(pagination.getPageNumber()).isEqualTo(2);
    assertThat(pagination.getPageSize()).isEqualTo(15);
    assertThat(pagination.getSort()).isEqualTo(Sort.by("dummy"));
  }

  private JhipsterSampleApplicationPageable pagination() {
    return new JhipsterSampleApplicationPageable(2, 15);
  }

  @Test
  void shouldNotConvertFromSpringPageWithoutSpringPage() {
    assertThatThrownBy(() -> JhipsterSampleApplicationPages.from(null, source -> source))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("springPage");
  }

  @Test
  void shouldNotConvertFromSpringPageWithoutMapper() {
    assertThatThrownBy(() -> JhipsterSampleApplicationPages.from(springPage(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("mapper");
  }

  @Test
  void shouldConvertFromSpringPage() {
    JhipsterSampleApplicationPage<String> page = JhipsterSampleApplicationPages.from(springPage(), Function.identity());

    assertThat(page.content()).containsExactly("test");
    assertThat(page.currentPage()).isEqualTo(2);
    assertThat(page.pageSize()).isEqualTo(10);
    assertThat(page.totalElementsCount()).isEqualTo(30);
  }

  private PageImpl<String> springPage() {
    return new PageImpl<>(List.of("test"), PageRequest.of(2, 10), 30);
  }
}
