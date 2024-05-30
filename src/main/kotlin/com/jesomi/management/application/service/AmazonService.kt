package com.jesomi.management.application.service

interface AmazonService {
    /**
     * S3 pre-signed-url 생성 요청
     * bucketName : 버킷 명
     * uploadFullPath : 업로드 될 경로 및 파일 명. ex) event/test1234.png
     */
    fun s3GeneratePreSignedUrl(bucketName: String, uploadFullPath: String): String

    /**
     * S3 파일 or 폴더 삭제
     * bucketName : 버킷 명
     * uploadFullPath : 업로드 될 경로 및 파일 명. ex) event/test1234.png
     */
    fun s3DeleteObject(bucketName: String, uploadFullPath: String)

    /**
     * S3 파일 or 폴더 존재 여부 체크
     * return 값 true 일 경우 존재 함
     * false 일 경우 존재 하지 않음
     */
    fun isS3FileExist(bucketName: String, uploadFullPath: String): Boolean
}