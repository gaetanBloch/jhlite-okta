package com.mycompany.myapp.shared.pagination.infrastructure.secondary;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mycompany.myapp.shared.error.domain.Assert;
import com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPage;
import com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPageable;

public final class JhipsterSampleApplicationPages {

  private JhipsterSampleApplicationPages() {}

  public static Pageable from(JhipsterSampleApplicationPageable pagination) {
    return from(pagination, Sort.unsorted());
  }

  public static Pageable from(JhipsterSampleApplicationPageable pagination, Sort sort) {
    Assert.notNull("pagination", pagination);
    Assert.notNull("sort", sort);

    return PageRequest.of(pagination.page(), pagination.pageSize(), sort);
  }

  public static <S, T> JhipsterSampleApplicationPage<T> from(Page<S> springPage, Function<S, T> mapper) {
    Assert.notNull("springPage", springPage);
    Assert.notNull("mapper", mapper);

    return JhipsterSampleApplicationPage
      .builder(springPage.getContent().stream().map(mapper).toList())
      .currentPage(springPage.getNumber())
      .pageSize(springPage.getSize())
      .totalElementsCount(springPage.getTotalElements())
      .build();
  }
}
