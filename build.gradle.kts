import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.7.20"  // 코틀린 JVM 플러그인
    kotlin("plugin.spring") version "1.7.20"  // 코틀린 스프링 플러그인

    kotlin("plugin.jpa") version "1.7.20"
//    kotlin("jvm") version "1.7.20"
//    application
}

group = "org.kotorial"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect") // 소스 코드가 코틀린으로 작성된 경우에 필요
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")  // 소스코드가 코틀린으로 작성된 경우에 필요

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(kotlin("test"))// https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")  // JSR-305와 연관된 널허용성 애노테이션
        jvmTarget = "1.8"
    }
}
