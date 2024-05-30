package com.jesomi.management.persistence.rdb.repository

import com.jesomi.management.persistence.rdb.entity.FileManage
import org.springframework.data.jpa.repository.JpaRepository

interface FileManageRepository: JpaRepository<FileManage, String>, FileManageRepositoryCustom {
}