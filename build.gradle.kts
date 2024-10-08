import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.graalvm.buildtools.native") version "0.9.28"
    id("com.google.cloud.tools.jib") version "3.4.0"
    id("jacoco")
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    kotlin("kapt") version "1.9.23"
    kotlin("plugin.allopen") version "1.9.24"
}

group = "com.jesomi"
version = "0.0.1-SNAPSHOT"
val queryDslVersion = "5.1.0"
val awsSpringCloudVersion = "3.2.0-M1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

jacoco {
    toolVersion = "0.8.12"
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    all {
        exclude(group = "commons-logging", module = "commons-logging")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // log tracing
    implementation("io.micrometer:micrometer-tracing-bridge-brave:1.3.1")
    // implementation("io.opentelemetry.instrumentation:opentelemetry-logback-mdc-1.0:1.24.0-alpha") otel 쓸 경우

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    kapt("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // AWS SDK
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:${awsSpringCloudVersion}"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs:${awsSpringCloudVersion}")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-secrets-manager:${awsSpringCloudVersion}")
    implementation("software.amazon.awssdk.crt:aws-crt:0.29.22")
    implementation("software.amazon.awssdk:s3:2.25.65")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    runtimeOnly("software.amazon.msk:aws-msk-iam-auth:2.2.0")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

// Querydsl 설정부 추가 - start
val generated = file("src/main/generated")

// querydsl QClass 파일 생성 위치를 지정
tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(generated)
}

// kotlin source set 에 querydsl QClass 위치 추가
sourceSets {
    main {
        kotlin.srcDirs += generated
    }
}

// gradle clean 시에 QClass 디렉토리 삭제
tasks.named("clean") {
    doLast {
        generated.deleteRecursively()
    }
}

kapt {
    generateStubs = true
}
// Querydsl 설정부 추가 - end

// entity allOpen
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

// jacoco
tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.required = true
    }
    dependsOn("test")
    finalizedBy("jacocoTestCoverageVerification")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            enabled = true
            element = "CLASS"

            limit {
                counter = "METHOD"
                value = "COVEREDRATIO"
                minimum = "0.0".toBigDecimal()
            }

            classDirectories.setFrom(
                files(classDirectories.files.map {
                    fileTree(it) {
                        include(
                            "com/jesomi/management/**",
                        )
                    }
                })
            )
        }
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

jib {
    container {
        creationTime = "USE_CURRENT_TIMESTAMP"
        mainClass = "com.jesomi.management.ManagementApplicationKt"
    }

    from {
        image = "ghcr.io/graalvm/jdk-community:21"
        platforms {
            platform {
                architecture = "amd64"
                os = "linux"
            }
        }
    }
}