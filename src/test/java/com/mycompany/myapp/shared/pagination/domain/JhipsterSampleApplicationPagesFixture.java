package com.mycompany.myapp.shared.pagination.domain;

import java.util.List;

import com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPage.JhipsterSampleApplicationPageBuilder;

public final class JhipsterSampleApplicationPagesFixture {

  private JhipsterSampleApplicationPagesFixture() {}

  public static JhipsterSampleApplicationPage<String> page() {
    return pageBuilder().build();
  }

  public static JhipsterSampleApplicationPageBuilder<String> pageBuilder() {
    return JhipsterSampleApplicationPage.builder(List.of("test")).currentPage(2).pageSize(10).totalElementsCount(21);
  }
}
