package com.neim.springboot.restservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(geApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.neim.springboot.restservices"))
                .paths(PathSelectors.ant("/users/**"))
                .build();
        }
    // Swagger metadata: http://localhost:8080/v2/api-docs
    // Swagger UI URL: http://localhost:8080/swagger-ui/

    private ApiInfo geApiInfo() {
        return new ApiInfoBuilder()
                .title("User management service")
                .description("This page list all APIs of User Management")
                .version("2.0")
                .contact(new Contact("Nestor Eduardo", "http://localhost:8080/v2/api-docs", "neim1996@hotmail.com"))
                .license("License 2.0")
                .licenseUrl("http://localhost:8080/swagger-ui/")
                .build();
    }
}
