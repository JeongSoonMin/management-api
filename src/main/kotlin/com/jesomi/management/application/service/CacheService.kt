package com.jesomi.management.application.service

import com.jesomi.management.application.model.CacheDto

interface CacheService {
    /**
     * 파일 관리 정보 캐시 조회(cacheable 처리 됨. 캐시에 없을 경우 DB 조회 해서 캐시 셋팅)
     * key : settlement-management:file-manage:{fileManageCd}
     */
    fun findFileManageCache(fileManageCd: String): CacheDto.FileManageCache?
}