package com.jesomi.management.persistence.rdb.repository

import com.jesomi.management.persistence.rdb.entity.FileManage
import java.util.*

interface FileManageRepositoryCustom {
    /**
     * 사용중인 파일 관리 정보 조회(fileManageCd 기준)
     */
    fun findByIdAndUsing(fileManageCd: String): Optional<FileManage>
}