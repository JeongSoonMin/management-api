package com.jesomi.management.persistence.rdb.repository

import com.jesomi.management.persistence.rdb.entity.FileUpload
import org.springframework.data.jpa.repository.JpaRepository

interface FileUploadRepository: JpaRepository<FileUpload, Long>, FileUploadRepositoryCustom {
}