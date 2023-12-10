package com.mycompany.myapp.shared.error_generator.infrastructure.primary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mycompany.myapp.shared.error.domain.JhipsterSampleApplicationException;
import com.mycompany.myapp.shared.error.domain.StandardErrorKey;

@RestController
@RequestMapping("/api/errors")
class JhipsterSampleApplicationErrorsResource {

  @GetMapping("bad-request")
  void getBadRequest() {
    throw JhipsterSampleApplicationException.badRequest(StandardErrorKey.BAD_REQUEST).addParameter("code", "400").build();
  }
}
