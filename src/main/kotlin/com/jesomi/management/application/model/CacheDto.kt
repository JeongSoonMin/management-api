package com.jesomi.management.application.model

import com.jesomi.management.persistence.rdb.entity.FileManage


class CacheDto {
    data class FileManageCache(
        val fileManageCd: String,
        var domainName: String,
        var bucketName: String,
        val uploadPath: String,
        val originFileNameYn: String
    ) {
        companion object {
            fun of(fileManage: FileManage): FileManageCache {
                return FileManageCache(
                    fileManage.fileManageCd,
                    fileManage.domainName,
                    fileManage.bucketName,
                    fileManage.uploadPath,
                    fileManage.originFileNameYn
                )
            }
        }
    }

}