package com.mycompany.myapp.shared.error.infrastructure.primary;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
class AssertionErrorsConfiguration {

  @Bean("assertionErrorMessageSource")
  MessageSource assertionErrorMessageSource() {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();

    source.setBasename("classpath:/messages/assertions-errors/assertion-errors-messages");
    source.setDefaultEncoding("UTF-8");

    return source;
  }
}
