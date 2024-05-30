package com.jesomi.management.persistence.rdb.repository.impl

import com.jesomi.management.persistence.rdb.entity.FileUpload
import com.jesomi.management.persistence.rdb.entity.QFileUpload.fileUpload
import com.jesomi.management.persistence.rdb.entity.QFileManage.fileManage
import com.jesomi.management.persistence.rdb.entity.QUser
import com.jesomi.management.persistence.rdb.enums.FileUploadStatus
import com.jesomi.management.persistence.rdb.model.FileUploadData
import com.jesomi.management.persistence.rdb.repository.FileUploadRepositoryCustom
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class FileUploadRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : FileUploadRepositoryCustom {
    override fun findByFileManageCdAndUsing(
        fileManageCd: String,
        pageable: Pageable
    ): Page<FileUpload> {
        val list = queryFactory.select(fileUpload)
            .from(fileUpload)
            .where(
                fileUpload.fileManageCd.eq(fileManageCd),
                fileUpload.uploadStatus.eq(FileUploadStatus.C)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(fileUpload.fileSeq.desc())
            .fetch()

        var count = queryFactory.select(fileUpload.count())
            .from(fileUpload)
            .where(
                fileUpload.fileManageCd.eq(fileManageCd),
                fileUpload.uploadStatus.eq(FileUploadStatus.C)
            )
            .fetchOne()

        if (count == null) count = 0

        return PageImpl(list, pageable, count)
    }

    override fun findByFileManageCdAndUsingJoinManage(
        fileManageCd: String,
        pageable: Pageable
    ): Page<FileUploadData> {
        val rUser = QUser("rUser")
        val uUser = QUser("uUser")

        val list = queryFactory.select(
            Projections.constructor(
                FileUploadData::class.java,
                fileUpload.fileSeq,
                fileManage.domainName,
                fileUpload.uploadPath,
                fileUpload.uploadFileName,
                fileUpload.originFileName,
                fileUpload.uploadStatus,
                rUser.userNm,
                fileUpload.regTime,
                uUser.userNm,
                fileUpload.updTime
            )
        )
            .from(fileUpload)
            .leftJoin(fileManage).on(fileUpload.fileManageCd.eq(fileManage.fileManageCd))
            .leftJoin(rUser).on(fileUpload.regId.eq(rUser.userId))
            .leftJoin(uUser).on(fileUpload.updId.eq(uUser.userId))
            .where(
                fileUpload.fileManageCd.eq(fileManageCd),
                fileUpload.uploadStatus.eq(FileUploadStatus.C)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(fileUpload.fileSeq.desc())
            .fetch()

        var count = queryFactory.select(fileUpload.count())
            .from(fileUpload)
            .where(
                fileUpload.fileManageCd.eq(fileManageCd),
                fileUpload.uploadStatus.eq(FileUploadStatus.C)
            )
            .fetchOne()

        if (count == null) count = 0

        return PageImpl(list, pageable, count)
    }

}