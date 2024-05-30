package com.jesomi.management.persistence.rdb.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnTransformer

@Entity
@Table(name = "tb_user")
class User(
    @Id
    @Column(name = "user_id")
    val userId: String?,

    @ColumnTransformer(
        forColumn = "user_nm",
        read = "FN_COM_GET_DECRYPT(user_nm)",
        write = "FN_COM_GET_ENCRYPT(?)"
    )
    @Column(name = "user_nm")
    val userNm: String?
) {
}

