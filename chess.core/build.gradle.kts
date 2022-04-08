plugins {
    kotlin("jvm") version "1.6.20"
}
repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(18)) // "8"
    }
}