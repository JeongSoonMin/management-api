package com.jesomi.management.application.service

interface FileManagementService {
    fun generatePreSignedUrl(uploadType: String, fileName: String): String?
}