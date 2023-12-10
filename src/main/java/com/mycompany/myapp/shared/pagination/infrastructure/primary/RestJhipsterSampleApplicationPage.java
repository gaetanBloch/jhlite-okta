package com.mycompany.myapp.shared.pagination.infrastructure.primary;

import java.util.List;
import java.util.function.Function;

import com.mycompany.myapp.shared.error.domain.Assert;
import com.mycompany.myapp.shared.pagination.domain.JhipsterSampleApplicationPage;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(name = "Page", description = "Paginated content")
public class RestJhipsterSampleApplicationPage<T> {

  private final List<T> content;
  private final int currentPage;
  private final int pageSize;
  private final long totalElementsCount;
  private final int pagesCount;
  private final boolean hasPrevious;
  private final boolean hasNext;

  private RestJhipsterSampleApplicationPage(RestJhipsterSampleApplicationPageBuilder<T> builder) {
    content = builder.content;
    currentPage = builder.currentPage;
    pageSize = builder.pageSize;
    totalElementsCount = builder.totalElementsCount;
    pagesCount = builder.pageCount;
    hasPrevious = builder.hasPrevious;
    hasNext = builder.hasNext;
  }

  public static <S, T> RestJhipsterSampleApplicationPage<T> from(JhipsterSampleApplicationPage<S> source, Function<S, T> mapper) {
    Assert.notNull("source", source);
    Assert.notNull("mapper", mapper);

    return new RestJhipsterSampleApplicationPageBuilder<>(source.content().stream().map(mapper).toList())
      .currentPage(source.currentPage())
      .pageSize(source.pageSize())
      .totalElementsCount(source.totalElementsCount())
      .pageCount(source.pageCount())
      .hasPrevious(source.hasPrevious())
      .hasNext(source.hasNext())
      .build();
  }

  @Schema(description = "Page content")
  public List<T> getContent() {
    return content;
  }

  @Schema(description = "Current page (starts at 0)", example = "0", requiredMode = RequiredMode.REQUIRED)
  public int getCurrentPage() {
    return currentPage;
  }

  @Schema(description = "Number of elements on each page", example = "10", requiredMode = RequiredMode.REQUIRED)
  public int getPageSize() {
    return pageSize;
  }

  @Schema(description = "Total number of elements to paginate", example = "100", requiredMode = RequiredMode.REQUIRED)
  public long getTotalElementsCount() {
    return totalElementsCount;
  }

  @Schema(description = "Number of resulting pages", example = "10", requiredMode = RequiredMode.REQUIRED)
  public int getPagesCount() {
    return pagesCount;
  }

  @Schema(description = "True is there is a previous page, false otherwise", requiredMode = RequiredMode.REQUIRED)
  public boolean getHasPrevious() {
    return hasPrevious;
  }

  @Schema(description = "True is there is a next page, false otherwise", requiredMode = RequiredMode.REQUIRED)
  public boolean getHasNext() {
    return hasNext;
  }

  private static class RestJhipsterSampleApplicationPageBuilder<T> {

    private final List<T> content;
    private int currentPage;
    private int pageSize;
    private long totalElementsCount;
    private int pageCount;
    private boolean hasPrevious;
    private boolean hasNext;

    private RestJhipsterSampleApplicationPageBuilder(List<T> content) {
      this.content = content;
    }

    public RestJhipsterSampleApplicationPageBuilder<T> pageSize(int pageSize) {
      this.pageSize = pageSize;

      return this;
    }

    public RestJhipsterSampleApplicationPageBuilder<T> currentPage(int currentPage) {
      this.currentPage = currentPage;

      return this;
    }

    public RestJhipsterSampleApplicationPageBuilder<T> totalElementsCount(long totalElementsCount) {
      this.totalElementsCount = totalElementsCount;

      return this;
    }

    public RestJhipsterSampleApplicationPageBuilder<T> pageCount(int pageCount) {
      this.pageCount = pageCount;

      return this;
    }

    public RestJhipsterSampleApplicationPageBuilder<T> hasPrevious(boolean hasPrevious) {
      this.hasPrevious = hasPrevious;

      return this;
    }

    public RestJhipsterSampleApplicationPageBuilder<T> hasNext(boolean hasNext) {
      this.hasNext = hasNext;

      return this;
    }

    public RestJhipsterSampleApplicationPage<T> build() {
      return new RestJhipsterSampleApplicationPage<>(this);
    }
  }
}
