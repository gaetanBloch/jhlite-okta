package com.mycompany.myapp.shared.pagination.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mycompany.myapp.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import com.mycompany.myapp.shared.error.domain.Assert;

public class JhipsterSampleApplicationPageable {

  private final int page;
  private final int pageSize;
  private final int offset;

  public JhipsterSampleApplicationPageable(int page, int pageSize) {
    Assert.field("page", page).min(0);
    Assert.field("pageSize", pageSize).min(1).max(100);

    this.page = page;
    this.pageSize = pageSize;
    offset = page * pageSize;
  }

  public int page() {
    return page;
  }

  public int pageSize() {
    return pageSize;
  }

  public int offset() {
    return offset;
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(page).append(pageSize).build();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    JhipsterSampleApplicationPageable other = (JhipsterSampleApplicationPageable) obj;
    return new EqualsBuilder().append(page, other.page).append(pageSize, other.pageSize).build();
  }
}
