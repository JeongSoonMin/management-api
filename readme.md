Management Application
==================================

## 개발 구성
* JDK 21.x
* SpringBoot 3.2.5
* Kotlin 1.9.23
* JPA + QueryDSL
* AWS SDK

## 로컬 개발 환경시 셋팅 필요 사항
* AWS Credential 셋팅 필요
1. AWS Console > IAM > 사용자 > AccessKey 발급
2. aws-cli 설치 필요
<pre><code>
// aws-cli 설치
$ brew install aws-cli
</code></pre>
3. aws configure
<pre><code>
$ aws configure
AWS Access Key ID [None]: XXXXXXXXXXXX
AWS Secret Access Key [None]: XXXXXXXXXXXXXXXXXXXXXXXXXX
Default region name [None]: ap-northeast-2
Default output format [None]: json
</code></pre>
4. config 확인
<pre><code>
$ cat .aws/credentials
$ cat .aws/config

OR

$ aws configure list
</code></pre>

* SpringBoot 구동시 환경변수 추가 필요. AWS SecretsManager 구성 되어 있을 경우 셋팅 필요 없음.
  * DB_MASTER_HOST=localhost
  * DB_MASTER_PORT=3306
  * DB_MASTER_DATABASE=xxxx
  * DB_MASTER_USERNAME=xxxx
  * DB_MASTER_PASSWORD=xxxx
  * DB_SLAVE_HOST=localhost
  * DB_SLAVE_PORT=3307
  * DB_SLAVE_DATABASE=xxxx
  * DB_SLAVE_USERNAME=xxxx
  * DB_SLAVE_PASSWORD=xxxx
  * REDIS_HOST=localhost
  * REDIS_PORT=6379

## 개발 사항
* 가상 스레드 적용(http request 및 async 적용)
* Sample CRUD
  * 페이징 처리 pageable 사용
* DB 멀티 커넥션 적용
  * master/slave 접속 host
  * routingDataSource 적용으로 조회성 쿼리 자동 slave 조회
* DB 정보 AWS SecretsManager 사용
* 파일 업로드 AWS S3 PreSignedUrl 요청 처리
* AWS SQS 메시지 put/listen 처리
* Redis cache 적용
  * cacheable
  * redisTemplate
  * CRUDRepository

