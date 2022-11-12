package com.b2list.test.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
class SwaggerConfiguration {
    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "B2LIST",
            "B2LIST development test project.",
            null,
            null,
            null,
            null,
            null,
            Collections.emptyList()
        )
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
            .paths(PathSelectors.any())
            .build().useDefaultResponseMessages(false)
    }
}