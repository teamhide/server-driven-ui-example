

plugins {
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("jacoco")
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
}

group = "com.teamhide"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.asyncer:r2dbc-mysql:1.1.2")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("io.micrometer:context-propagation:1.1.1")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("com.mysql:mysql-connector-j")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.112.Final:osx-aarch_64")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.0")
    testImplementation("io.kotest:kotest-framework-datatest:5.9.0")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("com.squareup.okhttp3:okhttp:4.11.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
}

val testAll by tasks.registering {
    dependsOn("test", "jacocoTestReport", "jacocoTestCoverageVerification")
    tasks["test"].mustRunAfter(tasks["ktlintCheck"])
    tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
    tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
}

val snippetsDir by extra { file("build/generated-snippets") }
tasks.test {
    useJUnitPlatform()
    systemProperties["spring.profiles.active"] = "test"
    outputs.dir(snippetsDir)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.required.set(true)
        xml.required.set(false)
        csv.required.set(false)
    }
    finalizedBy(tasks.jacocoTestCoverageVerification)
    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        "**/*Application*",
                        "**/*logger*",
                        "**/*Logger*",
                        "**/**Logger**.class",
                        "**/**logger**.class",
                        "**logger*",
                    )
                }
            }
        )
    )
}

tasks.jacocoTestCoverageVerification {
    val queryDslClasses = ('A'..'Z').map { "*.Q$it*" }
    violationRules {
        rule {
            element = "CLASS"
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal()
            }
            classDirectories.setFrom(sourceSets.main.get().output.asFileTree)
            excludes = listOf(
                "com.teamhide.serverdrivenui.ApplicationKt",
                "**/*logger*",
                "**/*Logger*",
                "**/**Logger**.class",
                "**/**logger**.class",
                "**logger*",
            ) + queryDslClasses
        }
    }
}
