package com.jesomi.management.persistence.rdb.entity

import com.jesomi.management.application.model.FileDto
import com.jesomi.management.persistence.rdb.enums.FileUploadStatus
import jakarta.persistence.*
import java.util.function.Supplier

@Entity
@Table(name = "tb_file_upload")
class FileUpload(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_seq")
    val fileSeq: Long? = null,

    @Column(name = "file_manage_cd")
    val fileManageCd: String,

    @Column(name = "bucket_name")
    val bucketName: String,

    @Column(name = "upload_path")
    val uploadPath: String,

    @Column(name = "upload_file_name")
    val uploadFileName: String,

    @Column(name = "origin_file_name")
    val originFileName: String,

    @Column(name = "reg_id")
    val regId: String?,

): BaseDateEntity() {
    @Column(name = "upload_status")
    @Enumerated(EnumType.STRING)
    var uploadStatus: FileUploadStatus = FileUploadStatus.C
        protected set

    @Column(name = "remark")
    var remark: String? = null
        protected set

    @Column(name = "upd_id")
    var updId: String? = this.regId
        protected set

    fun delete(updId: String) {
        this.uploadStatus = FileUploadStatus.D
        this.updId = updId
    }

    fun isDeleted(): Boolean {
        return FileUploadStatus.D == this.uploadStatus
    }

    fun isDeletedThrow(exceptionSupplier: Supplier<Throwable>): FileUpload {
        if (isDeleted()) throw exceptionSupplier.get()
        else return this
    }

    fun modify(request: FileDto.FileUploadModifyRequest, reqId: String) {
        this.remark = request.remark
        this.updId = reqId
    }
}