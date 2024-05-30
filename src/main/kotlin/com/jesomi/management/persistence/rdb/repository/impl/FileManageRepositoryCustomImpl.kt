package com.jesomi.management.persistence.rdb.repository.impl

import com.jesomi.management.persistence.rdb.entity.FileManage
import com.jesomi.management.persistence.rdb.entity.QFileManage.fileManage
import com.jesomi.management.persistence.rdb.repository.FileManageRepositoryCustom
import com.querydsl.jpa.impl.JPAQueryFactory
import java.util.*

class FileManageRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : FileManageRepositoryCustom {
    override fun findByIdAndUsing(fileManageCd: String): Optional<FileManage> {
        return Optional.ofNullable(
            queryFactory.select(fileManage)
                .from(fileManage)
                .where(
                    fileManage.fileManageCd.eq(fileManageCd),
                    fileManage.useYn.eq("Y")
                )
                .fetchOne()
        )
    }
}