package com.jesomi.management.persistence.rdb.enums

enum class FileUploadStatus(
    var description: String
) {
    C("업로드 완료"),
    D("삭제")
}