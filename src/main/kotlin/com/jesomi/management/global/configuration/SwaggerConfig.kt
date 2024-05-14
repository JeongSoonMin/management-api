package com.jesomi.management.global.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class SwaggerConfig {
    val SERVER_URL: String = "/"

    @Bean
    fun getOpenApi(): OpenAPI {
        return OpenAPI()
            .info(getApiInfo())
            .components(getComponents())
            .addServersItem(getServersItem())
            .addSecurityItem(getSecurityItem())
            .tags(getTags())
    }

    fun getApiInfo(): Info {
        return Info()
            .title("Admin Management 백엔드 API")
            .version("1.0.0")
            .description("Admin Management 백엔드 API")
    }

    fun getComponents(): Components {
        return Components()
    }

    fun getServersItem(): Server {
        return Server().url(SERVER_URL)
    }

    fun getSecurityItem(): SecurityRequirement {
        return SecurityRequirement()
            .addList("/swagger*")
    }

    fun getTags(): List<Tag> {
        return Arrays.asList(
            Tag().name(SwaggerTags.COMMON).description("공통 기능"),
            Tag().name(SwaggerTags.SAMPLE).description("샘플"),
            Tag().name(SwaggerTags.TEST).description("테스트"),
        )
    }

    object SwaggerTags {
        const val COMMON = "COMMON"
        const val SAMPLE = "SAMPLE"
        const val TEST = "TEST"
    }

}