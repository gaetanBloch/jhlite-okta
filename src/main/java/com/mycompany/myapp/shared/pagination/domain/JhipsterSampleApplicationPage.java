package com.mycompany.myapp.shared.pagination.domain;

import java.util.List;
import java.util.function.Function;

import com.mycompany.myapp.shared.collection.domain.JhipsterSampleApplicationCollections;
import com.mycompany.myapp.shared.error.domain.Assert;

public class JhipsterSampleApplicationPage<T> {

  private static final int MINIMAL_PAGE_COUNT = 1;
  
  private final List<T> content;
  private final int currentPage;
  private final int pageSize;
  private final long totalElementsCount;

  private JhipsterSampleApplicationPage(JhipsterSampleApplicationPageBuilder<T> builder) {
    content = JhipsterSampleApplicationCollections.immutable(builder.content);
    currentPage = builder.currentPage;
    pageSize = buildPageSize(builder.pageSize);
    totalElementsCount = buildTotalElementsCount(builder.totalElementsCount);
  }

  private int buildPageSize(int pageSize) {
    if (pageSize == -1) {
      return content.size();
    }

    return pageSize;
  }

  private long buildTotalElementsCount(long totalElementsCount) {
    if (totalElementsCount == -1) {
      return content.size();
    }

    return totalElementsCount;
  }

  public static <T> JhipsterSampleApplicationPage<T> singlePage(List<T> content) {
    return builder(content).build();
  }

  public static <T> JhipsterSampleApplicationPageBuilder<T> builder(List<T> content) {
    return new JhipsterSampleApplicationPageBuilder<>(content);
  }

  public static <T> JhipsterSampleApplicationPage<T> of(List<T> elements, JhipsterSampleApplicationPageable pagination) {
    Assert.notNull("elements", elements);
    Assert.notNull("pagination", pagination);

    List<T> content = elements.subList(
      Math.min(pagination.offset(), elements.size()),
      Math.min(pagination.offset() + pagination.pageSize(), elements.size())
    );

    return builder(content).currentPage(pagination.page()).pageSize(pagination.pageSize()).totalElementsCount(elements.size()).build();
  }

  public List<T> content() {
    return content;
  }

  public int currentPage() {
    return currentPage;
  }

  public int pageSize() {
    return pageSize;
  }

  public long totalElementsCount() {
    return totalElementsCount;
  }

  public int pageCount() {
    if (totalElementsCount > 0) {
      return (int) Math.ceil(totalElementsCount / (float) pageSize);
    }

    return MINIMAL_PAGE_COUNT;
  }

  public boolean hasPrevious() {
    return currentPage > 0;
  }

  public boolean hasNext() {
    return isNotLast();
  }

  public boolean isNotLast() {
    return currentPage + 1 < pageCount();
  }

  public <R> JhipsterSampleApplicationPage<R> map(Function<T, R> mapper) {
    Assert.notNull("mapper", mapper);

    return builder(content().stream().map(mapper).toList())
      .currentPage(currentPage)
      .pageSize(pageSize)
      .totalElementsCount(totalElementsCount)
      .build();
  }

  public static class JhipsterSampleApplicationPageBuilder<T> {

    private final List<T> content;
    private int currentPage;
    private int pageSize = -1;
    private long totalElementsCount = -1;

    private JhipsterSampleApplicationPageBuilder(List<T> content) {
      this.content = content;
    }

    public JhipsterSampleApplicationPageBuilder<T> pageSize(int pageSize) {
      this.pageSize = pageSize;

      return this;
    }

    public JhipsterSampleApplicationPageBuilder<T> currentPage(int currentPage) {
      this.currentPage = currentPage;

      return this;
    }

    public JhipsterSampleApplicationPageBuilder<T> totalElementsCount(long totalElementsCount) {
      this.totalElementsCount = totalElementsCount;

      return this;
    }

    public JhipsterSampleApplicationPage<T> build() {
      return new JhipsterSampleApplicationPage<>(this);
    }
  }
}
