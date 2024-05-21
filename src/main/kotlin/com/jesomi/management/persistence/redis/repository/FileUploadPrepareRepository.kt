package com.jesomi.management.persistence.redis.repository

import com.jesomi.management.persistence.redis.entity.FileUploadPrepare
import org.springframework.data.repository.CrudRepository

interface FileUploadPrepareRepository: CrudRepository<FileUploadPrepare, String> {
}