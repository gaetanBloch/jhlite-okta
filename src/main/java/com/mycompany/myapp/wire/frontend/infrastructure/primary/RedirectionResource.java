package com.mycompany.myapp.wire.frontend.infrastructure.primary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.mycompany.myapp.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@Controller
@ExcludeFromGeneratedCodeCoverage
class RedirectionResource {

  private static final String FORBID_EXTENSION_PATTERN = "/{file:[^\\.]*}";
  private static final String TRAILING_DIR_PATTERN = "/{trailingDir:.*}/";

  @GetMapping(
    value = {
      FORBID_EXTENSION_PATTERN,
      "/*" + FORBID_EXTENSION_PATTERN,
      "/*/*" + FORBID_EXTENSION_PATTERN,
      TRAILING_DIR_PATTERN,
      "/*" + TRAILING_DIR_PATTERN,
      "/*/*" + TRAILING_DIR_PATTERN,
    }
  )
  public String redirectApi() {
    return "forward:/";
  }
}
