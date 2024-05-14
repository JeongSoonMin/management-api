package com.jesomi.management.global.configuration

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.*
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@EnableCaching
@Configuration
@ConfigurationProperties("application.redis")
@EnableRedisRepositories(basePackages = ["com.jesomi.management.persistence.redis.repository"])
class RedisConfig {
    lateinit var host: String
    lateinit var port: String

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory { // Lettuce 기능 사용
        val lettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ZERO)
            .shutdownTimeout(Duration.ZERO)
            .build()
        val redisStandaloneConfiguration = RedisStandaloneConfiguration(host, port.toInt())
        return LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration)
    }

    @Bean("redisTemplate")
    fun redisTemplate(): RedisTemplate<*, *> {
        return RedisTemplate<Any, Any>().apply {
            this.connectionFactory = lettuceConnectionFactory()
            this.keySerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = GenericJackson2JsonRedisSerializer(getObjectMapper())
            this.valueSerializer = GenericJackson2JsonRedisSerializer(getObjectMapper())
        }
    }

    @Primary
    @Bean("defaultCacheManager")
    fun defaultCacheManager(): CacheManager {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .computePrefixWith { cacheName -> "management::$cacheName" }
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(getObjectMapper())
                )) // Serialize 관련 설정
            .entryTtl(Duration.ofMinutes(2)) // 캐시 기본 ttl 2분 설정
            .disableCachingNullValues() // Null 캐싱 제외
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(lettuceConnectionFactory())
            .cacheDefaults(configuration)
            .build()
    }

    @Bean("testCacheManager")
    fun testCacheManager(): CacheManager {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .computePrefixWith { cacheName -> "test::$cacheName" }
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(getObjectMapper())
                )) // Serialize 관련 설정
            .entryTtl(Duration.ofMinutes(10)) // 캐시 기본 ttl 10분 설정
            .disableCachingNullValues() // Null 캐싱 제외
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(lettuceConnectionFactory())
            .cacheDefaults(configuration)
            .build()
    }

    fun getObjectMapper(): ObjectMapper {
        /*
         TODO
          GenericJackson2JsonRedisSerializer 사용시 클래스 정보를 담지 않는 목적이지만,
          kotlin 에서 형변환 오류 발생으로 objectmapper 설정하여 사용하여야 하는데 objectmapper 설정시 클래스 정보가 다시 담겨 형변환이 진행 됨.
          objectMapper 사용에 대한 고려 필요.
         */
        val objectMapper = ObjectMapper().registerKotlinModule()
        var defaultTypeResolver: StdTypeResolverBuilder = ObjectMapper.DefaultTypeResolverBuilder(
            ObjectMapper.DefaultTyping.EVERYTHING,
            objectMapper.polymorphicTypeValidator
        )
        defaultTypeResolver = defaultTypeResolver.init(JsonTypeInfo.Id.CLASS, null)
        defaultTypeResolver = defaultTypeResolver.inclusion(JsonTypeInfo.As.PROPERTY)
        objectMapper.setDefaultTyping(defaultTypeResolver)

        return objectMapper
    }
}