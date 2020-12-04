package com.everis.exchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Value("${info.project.expose.package}")
  private String basePackage;

  @Value("${info.project.version}")
  private String description;

  @Value("${info.project.title}")
  private String title;

  @Value("${info.project.description}")
  private String version;

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage(basePackage))
        .build();
  }

  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder()
        .description(description)
        .title(title)
        .version(version)
        .build();
  }
}

