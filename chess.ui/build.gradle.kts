import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application

}
repositories {
    mavenCentral()
}
group = "chess.core"

dependencies {
    implementation(project(":chess.core"))
    implementation(fileTree("./libs"))
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(18)) // "8"
    }
}



application {
    mainClass.set("chess.core.Main")
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("chess-core")
    archiveVersion.set("1.0.0")
}