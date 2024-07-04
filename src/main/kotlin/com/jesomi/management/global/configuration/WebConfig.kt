package com.jesomi.management.global.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3003", "http://localhost:9000", "https://dev-gw.jesomi.com", "https://stg-gw.jesomi.com", "https://gw.jesomi.com")
            .allowedMethods("*")
            .allowCredentials(true)
            .maxAge(3600)
    }
}