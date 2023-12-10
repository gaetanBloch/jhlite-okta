package com.mycompany.myapp.shared.error.infrastructure.primary;

import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.mycompany.myapp.shared.enumeration.domain.Enums;
import com.mycompany.myapp.shared.error.domain.ErrorKey;
import com.mycompany.myapp.shared.error.domain.JhipsterSampleApplicationException;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1000)
class JhipsterSampleApplicationErrorsHandler {

  private static final Logger log = LoggerFactory.getLogger(JhipsterSampleApplicationErrorsHandler.class);
  private static final String MESSAGES_PREFIX = "error.";

  private final MessageSource messages;

  public JhipsterSampleApplicationErrorsHandler(@Qualifier("applicationErrorMessageSource") MessageSource messages) {
    Locale.setDefault(Locale.ENGLISH);

    this.messages = messages;
  }

  @ExceptionHandler(JhipsterSampleApplicationException.class)
  ProblemDetail handleJhipsterSampleApplicationException(JhipsterSampleApplicationException exception) {
    HttpStatus status = Optional.ofNullable(Enums.map(exception.status(), HttpStatus.class)).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, buildDetail(exception));

    problem.setTitle(getMessage(exception.key(), "title"));
    problem.setProperty("key", exception.key().get());

    logException(exception, status);

    return problem;
  }

  private String buildDetail(JhipsterSampleApplicationException exception) {
    String messageTemplate = getMessage(exception.key(), "detail");

    return ArgumentsReplacer.replaceParameters(messageTemplate, exception.parameters());
  }

  private String getMessage(ErrorKey key, String suffix) {
    return messages.getMessage(MESSAGES_PREFIX + key.get() + "." + suffix, null, locale());
  }

  private Locale locale() {
    return LocaleContextHolder.getLocale();
  }

  private void logException(JhipsterSampleApplicationException exception, HttpStatus status) {
    if (status.is4xxClientError()) {
      log.info(exception.getMessage(), exception);
    } else {
      log.error(exception.getMessage(), exception);
    }
  }
}
