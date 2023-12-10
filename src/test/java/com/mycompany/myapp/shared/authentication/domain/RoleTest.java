package com.mycompany.myapp.shared.authentication.domain;

import static org.assertj.core.api.Assertions.*;

import com.mycompany.myapp.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class RoleTest {

  @Test
  void shouldGetRoleKey() {
    assertThat(Role.ADMIN.key()).isEqualTo("ROLE_ADMIN");
  }

  @Test
  void shouldConvertUnknownRoleToUnknownRole() {
    assertThat(Role.from("ROLE_DUMMY")).isEqualTo(Role.UNKNOWN);
  }

  @Test
  void shouldConvertFromRole() {
    assertThat(Role.from("ROLE_ADMIN")).isEqualTo(Role.ADMIN);
  }
}
