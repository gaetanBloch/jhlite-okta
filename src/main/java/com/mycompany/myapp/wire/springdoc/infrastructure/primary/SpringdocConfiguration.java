package com.mycompany.myapp.wire.springdoc.infrastructure.primary;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mycompany.myapp.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@Configuration
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing technical configuration")
class SpringdocConfiguration {

  @Value("${application.version:undefined}")
  private String version;

  @Bean
  public OpenAPI jhipsterSampleApplicationOpenAPI() {
    return new OpenAPI().info(swaggerInfo()).externalDocs(swaggerExternalDoc());
  }

  private Info swaggerInfo() {
    return new Info()
      .title("Project API")
      .description("Project description API")
      .version(version)
      .license(new License().name("No license").url(""));
  }

  private ExternalDocumentation swaggerExternalDoc() {
    return new ExternalDocumentation().description("Project Documentation").url("");
  }

  @Bean
  public GroupedOpenApi jhipsterSampleApplicationAllOpenAPI() {
    return GroupedOpenApi.builder().group("all").pathsToMatch("/api/**").build();
  }
}
