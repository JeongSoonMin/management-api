package com.jesomi.management.application.service.impl

import com.jesomi.management.application.model.CacheDto
import com.jesomi.management.application.service.CacheService
import com.jesomi.management.global.common.log
import com.jesomi.management.persistence.rdb.repository.FileManageRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CacheServiceImpl(
    private val fileManageRepository: FileManageRepository
) : CacheService {
    val log = this.log()

    @Cacheable(value = ["file-manage"], key = "#fileManageCd", unless = "#result == null")
    override fun findFileManageCache(
        fileManageCd: String
    ): CacheDto.FileManageCache? {
        val fileManage = fileManageRepository.findByIdAndUsing(fileManageCd)
        log.info("findFileManageCache {} db 조회 완료", fileManageCd)
        if (fileManage.isPresent)
            return CacheDto.FileManageCache.of(fileManage.get())
        return null
    }
}