package com.mycompany.myapp.shared.pagination.domain;

import static org.assertj.core.api.Assertions.*;
import static com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPagesFixture.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.MissingMandatoryValueException;

@UnitTest
class JhipsterSampleApplicationPageTest {

  @Test
  void shouldGetEmptySinglePageWithoutContent() {
    JhipsterSampleApplicationPage<String> page = JhipsterSampleApplicationPage.singlePage(null);

    assertEmptyPage(page);
  }

  @Test
  void shouldGetEmptySinglePageFromBuilderWithoutContent() {
    JhipsterSampleApplicationPage<?> page = JhipsterSampleApplicationPage.builder(null).build();

    assertEmptyPage(page);
  }

  private void assertEmptyPage(JhipsterSampleApplicationPage<?> page) {
    assertThat(page.content()).isEmpty();
    assertThat(page.currentPage()).isZero();
    assertThat(page.pageSize()).isZero();
    assertThat(page.totalElementsCount()).isZero();
  }

  @Test
  void shouldGetSinglePage() {
    JhipsterSampleApplicationPage<String> page = JhipsterSampleApplicationPage.singlePage(List.of("test", "dummy"));

    assertSinglePage(page);
  }

  @Test
  void shouldGetSinglePageFromBuilderWithContentOnly() {
    JhipsterSampleApplicationPage<String> page = JhipsterSampleApplicationPage.builder(List.of("test", "dummy")).build();

    assertSinglePage(page);
  }

  private void assertSinglePage(JhipsterSampleApplicationPage<String> page) {
    assertThat(page.content()).containsExactly("test", "dummy");
    assertThat(page.currentPage()).isZero();
    assertThat(page.pageSize()).isEqualTo(2);
    assertThat(page.totalElementsCount()).isEqualTo(2);
    assertThat(page.pageCount()).isEqualTo(1);
  }

  @Test
  void shouldGetFullPage() {
    JhipsterSampleApplicationPage<String> page = pageBuilder().build();

    assertThat(page.content()).containsExactly("test");
    assertThat(page.currentPage()).isEqualTo(2);
    assertThat(page.pageSize()).isEqualTo(10);
    assertThat(page.totalElementsCount()).isEqualTo(21);
    assertThat(page.pageCount()).isEqualTo(3);
  }

  @Test
  void shouldNotMapWithoutMapper() {
    assertThatThrownBy(() -> pageBuilder().build().map(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("mapper");
  }

  @Test
  void shouldMapPage() {
    JhipsterSampleApplicationPage<String> page = pageBuilder().build().map(entry -> "hey");

    assertThat(page.content()).containsExactly("hey");
    assertThat(page.currentPage()).isEqualTo(2);
    assertThat(page.pageSize()).isEqualTo(10);
    assertThat(page.totalElementsCount()).isEqualTo(21);
    assertThat(page.pageCount()).isEqualTo(3);
  }

  @Test
  void shouldNotBeLastForFirstPage() {
    assertThat(pageBuilder().currentPage(0).build().isNotLast()).isTrue();
  }

  @Test
  void shouldBeLastWithOnePage() {
    assertThat(JhipsterSampleApplicationPage.singlePage(List.of("d")).isNotLast()).isFalse();
  }

  @Test
  void shouldBeLastPageWithoutContent() {
    JhipsterSampleApplicationPage<Object> page = JhipsterSampleApplicationPage.builder(List.of()).currentPage(0).pageSize(1).totalElementsCount(0).build();
    assertThat(page.isNotLast()).isFalse();
  }

  @Test
  void shouldBeLastForLastPage() {
    assertThat(pageBuilder().currentPage(2).build().isNotLast()).isFalse();
  }

  @Test
  void shouldGetPageFromElements() {
    JhipsterSampleApplicationPage<String> page = JhipsterSampleApplicationPage.of(List.of("hello", "java", "world"), new JhipsterSampleApplicationPageable(1, 1));

    assertThat(page.currentPage()).isEqualTo(1);
    assertThat(page.hasNext()).isTrue();
    assertThat(page.hasPrevious()).isTrue();
    assertThat(page.pageCount()).isEqualTo(3);
    assertThat(page.pageSize()).isEqualTo(1);
    assertThat(page.content()).containsExactly("java");
  }

  @Test
  void shouldGetEmptyPageFromOutOfBoundElements() {
    JhipsterSampleApplicationPage<String> page = JhipsterSampleApplicationPage.of(List.of("hello", "java", "world"), new JhipsterSampleApplicationPageable(4, 1));

    assertThat(page.currentPage()).isEqualTo(4);
    assertThat(page.hasNext()).isFalse();
    assertThat(page.hasPrevious()).isTrue();
    assertThat(page.pageCount()).isEqualTo(3);
    assertThat(page.pageSize()).isEqualTo(1);
    assertThat(page.content()).isEmpty();
  }

  @Test
  void shouldGetPageWithLessThanExpectedElements() {
    JhipsterSampleApplicationPage<String> page = JhipsterSampleApplicationPage.of(List.of("hello", "java", "world"), new JhipsterSampleApplicationPageable(0, 4));

    assertThat(page.currentPage()).isZero();
    assertThat(page.hasNext()).isFalse();
    assertThat(page.hasPrevious()).isFalse();
    assertThat(page.pageCount()).isEqualTo(1);
    assertThat(page.pageSize()).isEqualTo(4);
    assertThat(page.content()).hasSize(3);
  }
}
