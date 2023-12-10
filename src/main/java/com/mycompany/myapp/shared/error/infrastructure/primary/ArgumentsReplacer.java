package com.mycompany.myapp.shared.error.infrastructure.primary;

import java.util.Map;

final class ArgumentsReplacer {

  private static final String OPEN = "{{ ";
  private static final String CLOSE = " }}";

  private final Map<String, ? extends Object> arguments;

  private ArgumentsReplacer(Map<String, ? extends Object> arguments) {
    this.arguments = arguments;
  }

  public static String replaceParameters(String message, Map<String, ? extends Object> arguments) {
    if (message == null || arguments == null) {
      return message;
    }

    return new ArgumentsReplacer(arguments).format(message);
  }

  private String format(String message) {
    StringBuilder result = new StringBuilder(message);

    int lastMustaches = result.indexOf(OPEN);
    while (lastMustaches != -1) {
      int end = result.indexOf(CLOSE, lastMustaches);
      String key = result.substring(lastMustaches + OPEN.length(), end).trim();

      result.replace(lastMustaches, end + CLOSE.length(), getArgumentValue(key));

      lastMustaches = result.indexOf(OPEN, lastMustaches + 2);
    }

    return result.toString();
  }

  private String getArgumentValue(String key) {
    Object value = arguments.get(key);
    if (value == null) {
      return "{{ " + key + " }}";
    }

    return value.toString();
  }
}
