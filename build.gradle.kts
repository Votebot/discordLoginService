import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
    kotlin("jvm") version "1.3.71"
}

group = "space.votebot"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    jcenter()
    maven("https://kotlin.bintray.com/ktor")
}

dependencies {
    // Logging
    implementation("org.slf4j", "slf4j-api", "2.0.0-alpha1")
    implementation("ch.qos.logback", "logback-classic", "1.3.0-alpha5")
    implementation("io.github.microutils", "kotlin-logging", "1.7.9")

    // Sentry
    implementation("io.sentry", "sentry", "1.7.30")
    implementation("io.sentry", "sentry-logback", "1.7.30")

    // Metrics
    // implementation("com.influxdb", "influxdb-client-java", "1.6.0")

    // Ktor
    implementation("io.ktor", "ktor-server-netty", "1.3.2")
    implementation("io.ktor", "ktor-server-core", "1.3.2")
    implementation("io.ktor", "ktor-websockets", "1.3.2")
    implementation("io.ktor", "ktor-jackson", "1.3.2")
    testImplementation("io.ktor", "ktor-server-tests", "1.3.2")

    // Core
    implementation("org.kodein.di:kodein-di-generic-jvm:6.5.5")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-controller-jvm:6.5.5")
    implementation("com.google.firebase", "firebase-admin", "6.12.2")

    // Util
    implementation("io.github.cdimascio", "java-dotenv", "5.1.4")
    implementation("com.squareup.okhttp3", "okhttp", "4.5.0")

    // Kotlin
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", "1.3.5")

    // Tests
    testImplementation("org.jetbrains.kotlin", "kotlin-test-junit5", "1.3.71")
    testImplementation("org.mockito", "mockito-core", "3.3.3")
    testImplementation("com.nhaarman.mockitokotlin2", "mockito-kotlin", "2.2.0")
    testImplementation("io.zonky.test", "embedded-postgres", "1.2.6")
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.6.2")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.6.2")

}

application {
    mainClassName = "space.votebot.discordlogin.LauncherKt"
}



tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "13"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }

    "shadowJar"(ShadowJar::class) {
        archiveFileName.set("service.jar")
    }

    test {
        useJUnitPlatform()
    }
}
