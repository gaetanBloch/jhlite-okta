package com.mycompany.myapp.shared.error.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.AssertionErrorType;

@UnitTest
class AssertionErrorMessagesTest {

  private static final Map<String, Properties> ALL_ASSERTION_MESSAGES = loadMessages();

  private static Map<String, Properties> loadMessages() {
    try {
      return Files
        .list(Paths.get("src/main/resources/messages/assertions-errors"))
        .collect(Collectors.toUnmodifiableMap(Path::toString, toProperties()));
    } catch (IOException e) {
      throw new AssertionError();
    }
  }

  private static Function<Path, Properties> toProperties() {
    return file -> {
      Properties properties = new Properties();
      try {
        properties.load(Files.newInputStream(file));
      } catch (IOException e) {
        throw new AssertionError();
      }

      return properties;
    };
  }

  @Test
  void shouldHaveAssertionErrorTitleInAllSupportedLanguages() {
    Stream
      .of(AssertionErrorType.values())
      .map(assertionError -> "assertion-error." + assertionError.name() + ".title")
      .forEach(assertHasMessage());
  }

  @Test
  void shouldHaveAssertionErrorDetailInAllSupportedLanguages() {
    Stream
      .of(AssertionErrorType.values())
      .map(assertionError -> "assertion-error." + assertionError.name() + ".detail")
      .forEach(assertHasMessage());
  }

  private Consumer<String> assertHasMessage() {
    return messageKey ->
      ALL_ASSERTION_MESSAGES.forEach((file, localeMessages) ->
        assertThat(localeMessages).as(() -> "Missing " + messageKey + " translation in " + file).containsKey(messageKey)
      );
  }
}
