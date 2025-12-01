plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(
        libs.versions.jdk.version
            .get()
            .toInt(),
    )
}

ktlint {
    version.set(
        libs.versions.ktlint.version
            .get(),
    )
}
