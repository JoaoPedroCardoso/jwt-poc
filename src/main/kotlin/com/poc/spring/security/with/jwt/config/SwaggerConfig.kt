package com.poc.spring.security.with.jwt.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.poc.spring.security.with.jwt"))
            .paths(PathSelectors.ant("/api/**"))
            .build()
            .apiInfo(apiInfo())

    private fun apiInfo(): ApiInfo = ApiInfo(
            "Jwt Poc Doc API",
            "Some custom description of API.",
            "API JWT POC",
            "Terms of service",
            Contact("João Pedro", "https://github.com/JoaoPedroCardoso", "joaopedrocar@hotmail.com"),
            "License of API", "API license URL", emptyList())

}
