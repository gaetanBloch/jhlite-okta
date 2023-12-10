package com.mycompany.myapp.account.infrastructure.primary;

import static com.mycompany.myapp.account.domain.AccountsFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.UnitTest;

@UnitTest
class RestAccountTest {

  private static final ObjectMapper json = new ObjectMapper();

  @Test
  void shouldSerializeToJson() throws JsonProcessingException {
    assertThat(json.writeValueAsString(RestAccount.from(account()))).isEqualTo(json());
  }

  private String json() {
    return """
        {\
        "username":"user",\
        "name":"Paul DUPOND",\
        "email":"email@company.fr",\
        "roles":["ROLE_ADMIN"]\
        }\
        """;
  }

}
