package com.jesomi.management.persistence.redis.constants

object RedisConst {
    // CacheManager 에서 사용시 TTL 분 단위 시간 설정
    const val DEFAULT_CACHE_MANAGER_TTL: Long = 60L // 1시간

    // RedisHash 에서 사용시 TTL 초 단위 시간 설정
    const val FILE_MANAGEMENT_PREPARE_TTL: Long = 20 * 60L // 20분
}