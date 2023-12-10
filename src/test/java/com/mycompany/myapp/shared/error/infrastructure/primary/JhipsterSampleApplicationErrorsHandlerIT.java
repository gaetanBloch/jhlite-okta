package com.mycompany.myapp.shared.error.infrastructure.primary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import com.mycompany.myapp.IntegrationTest;

@IntegrationTest
@AutoConfigureMockMvc
class JhipsterSampleApplicationErrorsHandlerIT {

  @Autowired
  private MockMvc rest;

  @Test
  void shouldHandleJhipsterSampleApplicationErrorWithoutLocale() throws Exception {
    rest
      .perform(get("/api/errors/bad-request"))
      .andExpect(status().is4xxClientError())
      .andExpect(jsonPath("title").value("Bad request"))
      .andExpect(jsonPath("detail").value("You send a bad request: 400"));
  }

  @Test
  void shouldHandleJhipsterSampleApplicationErrorForFrenchLocale() throws Exception {
    rest
      .perform(get("/api/errors/bad-request").locale(Locale.FRANCE))
      .andExpect(status().is4xxClientError())
      .andExpect(jsonPath("title").value("Bad request"))
      .andExpect(jsonPath("detail").value("Votre requête n'est pas valide: 400"));
  }
}
