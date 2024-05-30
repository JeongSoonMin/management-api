package com.jesomi.management.persistence.rdb.repository

import com.jesomi.management.persistence.rdb.entity.FileUpload
import com.jesomi.management.persistence.rdb.model.FileUploadData
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FileUploadRepositoryCustom {
    /**
     * 파일 업로드 목록 조회(업로드 완료된 목록)
     */
    fun findByFileManageCdAndUsing(fileManageCd: String, pageable: Pageable): Page<FileUpload>

    /**
     * 파일 업로드 목록 조회(업로드 완료된 목록, 파일 관리 정보 포함)
     */
    fun findByFileManageCdAndUsingJoinManage(fileManageCd: String, pageable: Pageable): Page<FileUploadData>
}