package com.mycompany.myapp.shared.error.infrastructure.primary;

import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import com.mycompany.myapp.Logs;
import com.mycompany.myapp.LogsSpy;
import com.mycompany.myapp.LogsSpyExtension;
import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.AssertionErrorType;
import com.mycompany.myapp.shared.error.domain.AssertionException;
import com.mycompany.myapp.shared.error_generator.domain.NullElementInCollectionExceptionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.MessageSource;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class AssertionErrorsHandlerTest {

  private static final AssertionErrorsHandler handler = new AssertionErrorsHandler(mock(MessageSource.class));

  @Logs
  private LogsSpy logs;

  @Test
  void shouldLogPrimaryAssertionExceptionInInfo() {
    handler.handleAssertionError(new DefaultAssertionException());

    logs.shouldHave(Level.INFO, "Oops");
  }

  @Test
  void shouldLogDomainAssertionExceptionInError() {
    handler.handleAssertionError(NullElementInCollectionExceptionFactory.nullElementInCollection());

    logs.shouldHave(Level.ERROR, "a null element");
  }

  private static class DefaultAssertionException extends AssertionException {

    protected DefaultAssertionException() {
      super("field", "Oops");
    }

    @Override
    public AssertionErrorType type() {
      return AssertionErrorType.MISSING_MANDATORY_VALUE;
    }
  }
}
