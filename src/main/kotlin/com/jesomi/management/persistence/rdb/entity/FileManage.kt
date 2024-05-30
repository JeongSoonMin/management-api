package com.jesomi.management.persistence.rdb.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy

@Entity
@Table(name = "tb_file_manage")
class FileManage(
    @Id
    @Column(name = "file_manage_cd")
    val fileManageCd: String,

    @Column(name = "domain_name")
    var domainName: String,

    @Column(name = "bucket_name")
    var bucketName: String,

    @Column(name = "upload_path")
    val uploadPath: String,

    @Column(name = "origin_file_name_yn")
    val originFileNameYn: String,

    @Column(name = "use_yn")
    val useYn: String,

    @Column(name = "reg_id")
    @CreatedBy
    var regId: String?,

    @Column(name = "upd_id")
    @LastModifiedBy
    var updId: String?,
): BaseDateEntity(){
}
