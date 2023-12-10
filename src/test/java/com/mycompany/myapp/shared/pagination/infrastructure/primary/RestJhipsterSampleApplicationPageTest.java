package com.mycompany.myapp.shared.pagination.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPagesFixture.*;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.JsonHelper;
import com.mycompany.myapp.shared.error.domain.MissingMandatoryValueException;

@UnitTest
class RestJhipsterSampleApplicationPageTest {

  @Test
  void shouldNotConvertWithoutSourcePage() {
    assertThatThrownBy(() -> RestJhipsterSampleApplicationPage.from(null, source -> "test")).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldNotConvertWithoutMappingFunction() {
    assertThatThrownBy(() -> RestJhipsterSampleApplicationPage.from(page(), null)).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldMapFromDomainPage() {
    RestJhipsterSampleApplicationPage<String> page = RestJhipsterSampleApplicationPage.from(page(), Function.identity());

    assertThat(page.getContent()).containsExactly("test");
    assertThat(page.getCurrentPage()).isEqualTo(2);
    assertThat(page.getPageSize()).isEqualTo(10);
    assertThat(page.getTotalElementsCount()).isEqualTo(21);
    assertThat(page.getPagesCount()).isEqualTo(3);
  }

  @Test
  void shouldGetPageCountForPageLimit() {
    RestJhipsterSampleApplicationPage<String> page = RestJhipsterSampleApplicationPage.from(pageBuilder().totalElementsCount(3).pageSize(3).build(), Function.identity());

    assertThat(page.getPagesCount()).isEqualTo(1);
  }

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestJhipsterSampleApplicationPage.from(page(), Function.identity()))).isEqualTo(json());
  }

  private String json() {
    return """
        {"content":["test"],\
        "currentPage":2,\
        "pageSize":10,\
        "totalElementsCount":21,\
        "pagesCount":3,\
        "hasPrevious":true,\
        "hasNext":false\
        }\
        """;
  }
}
