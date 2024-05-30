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
            fun of(cmsFileManage: FileManage): FileManageCache {
                return FileManageCache(
                    cmsFileManage.fileManageCd,
                    cmsFileManage.domainName,
                    cmsFileManage.bucketName,
                    cmsFileManage.uploadPath,
                    cmsFileManage.originFileNameYn
                )
            }
        }
    }

}