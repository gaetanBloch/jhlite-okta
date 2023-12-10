package com.mycompany.myapp.shared.error.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.infrastructure.primary.JhipsterSampleApplicationExceptionFactory;

@UnitTest
class JhipsterSampleApplicationExceptionTest {

  @Test
  void shouldGetMinimalJhipsterSampleApplicationExceptionFromDomain() {
    JhipsterSampleApplicationException exception = JhipsterSampleApplicationException.builder(null).build();

    assertThat(exception.key()).isEqualTo(StandardErrorKey.INTERNAL_SERVER_ERROR);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.getMessage()).isEqualTo("An error occurred");
    assertThat(exception.getCause()).isNull();
    assertThat(exception.parameters()).isEmpty();
  }

  @Test
  void shouldGetMinimalJhipsterSampleApplicationExceptionFromPrimary() {
    JhipsterSampleApplicationException exception = JhipsterSampleApplicationExceptionFactory.buildEmptyException();

    assertThat(exception.key()).isEqualTo(StandardErrorKey.INTERNAL_SERVER_ERROR);
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.getMessage()).isEqualTo("An error occurred");
    assertThat(exception.getCause()).isNull();
    assertThat(exception.parameters()).isEmpty();
  }

  @Test
  void shouldGetFullJhipsterSampleApplicationException() {
    RuntimeException cause = new RuntimeException();
    JhipsterSampleApplicationException exception = JhipsterSampleApplicationException
      .builder(StandardErrorKey.BAD_REQUEST)
      .message("This is an error")
      .cause(cause)
      .addParameter("parameter", "value")
      .addParameters(Map.of("key", "value"))
      .status(ErrorStatus.BAD_REQUEST)
      .build();

    assertThat(exception.key()).isEqualTo(StandardErrorKey.BAD_REQUEST);
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.getMessage()).isEqualTo("This is an error");
    assertThat(exception.getCause()).isEqualTo(cause);
    assertThat(exception.parameters()).containsOnly(entry("parameter", "value"), entry("key", "value"));
  }

  @Test
  void shouldGetTechnicalErrorExceptionFromMessage() {
    JhipsterSampleApplicationException exception = JhipsterSampleApplicationException.technicalError("This is a problem");

    assertThat(exception.getMessage()).isEqualTo("This is a problem");
    assertThat(exception.key()).isEqualTo(StandardErrorKey.INTERNAL_SERVER_ERROR);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  void shouldGetTechnicalErrorException() {
    RuntimeException cause = new RuntimeException();
    JhipsterSampleApplicationException exception = JhipsterSampleApplicationException.technicalError("This is a problem", cause);

    assertThat(exception.getMessage()).isEqualTo("This is a problem");
    assertThat(exception.key()).isEqualTo(StandardErrorKey.INTERNAL_SERVER_ERROR);
    assertThat(exception.getCause()).isEqualTo(cause);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  void shouldGetInternalServerErrorShortcut() {
    JhipsterSampleApplicationException exception = JhipsterSampleApplicationException.internalServerError(StandardErrorKey.INTERNAL_SERVER_ERROR).build();

    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  void shouldGetBadRequestShortcut() {
    JhipsterSampleApplicationException exception = JhipsterSampleApplicationException.badRequest(StandardErrorKey.BAD_REQUEST).build();

    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
  }
}
