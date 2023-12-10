package com.mycompany.myapp.shared.error.infrastructure.primary;

import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.MessageSource;
import com.mycompany.myapp.Logs;
import com.mycompany.myapp.LogsSpy;
import com.mycompany.myapp.LogsSpyExtension;
import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.JhipsterSampleApplicationException;
import com.mycompany.myapp.shared.error.domain.StandardErrorKey;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class JhipsterSampleApplicationErrorsHandlerTest {

  private static final JhipsterSampleApplicationErrorsHandler handler = new JhipsterSampleApplicationErrorsHandler(mock(MessageSource.class));

  @Logs
  private LogsSpy logs;

  @Test
  void shouldLogServerErrorAsError() {
    handler.handleJhipsterSampleApplicationException(
      JhipsterSampleApplicationException.internalServerError(StandardErrorKey.INTERNAL_SERVER_ERROR).message("Oops").build()
    );

    logs.shouldHave(Level.ERROR, "Oops");
  }

  @Test
  void shouldLogClientErrorAsInfo() {
    handler.handleJhipsterSampleApplicationException(JhipsterSampleApplicationException.badRequest(StandardErrorKey.BAD_REQUEST).message("Oops").build());

    logs.shouldHave(Level.INFO, "Oops");
  }
}
