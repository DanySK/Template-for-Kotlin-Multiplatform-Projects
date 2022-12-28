@file:Suppress("UnstableApiUsage")

import org.danilopianini.gradle.mavencentral.JavadocJar
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.gradle.internal.os.OperatingSystem

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.multiJvmTesting)
    alias(libs.plugins.npm.publish)
    alias(libs.plugins.publishOnCentral)
    alias(libs.plugins.taskTree)
}

group = "org.danilopianini"

repositories {
    google()
    mavenCentral()
}

fun KotlinMultiplatformExtension.configureDarwinCompatiblePlatforms(nativeSetup: KotlinNativeTarget.() -> Unit) {
    macosX64(nativeSetup)
    macosArm64(nativeSetup)

    ios(nativeSetup)
    iosSimulatorArm64(nativeSetup)
    tvos(nativeSetup)
    tvosSimulatorArm64(nativeSetup)
    // If watchos fails is due to https://youtrack.jetbrains.com/issue/KT-54814
    watchos(nativeSetup)
    watchosSimulatorArm64(nativeSetup)
}

fun KotlinMultiplatformExtension.configureWindowsCompatiblePlatforms(nativeSetup: KotlinNativeTarget.() -> Unit) {
    mingwX64(nativeSetup)
}

fun KotlinMultiplatformExtension.configureLinuxCompatiblePlatforms(nativeSetup: KotlinNativeTarget.() -> Unit) {
    linuxX64(nativeSetup)
}

fun KotlinMultiplatformExtension.configureAllPlatforms(nativeSetup: KotlinNativeTarget.() -> Unit) {
    configureLinuxCompatiblePlatforms(nativeSetup)
    configureWindowsCompatiblePlatforms(nativeSetup)
    configureDarwinCompatiblePlatforms(nativeSetup)
    linuxArm32Hfp(nativeSetup)
    linuxArm64(nativeSetup)
    mingwX86(nativeSetup)
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val commonMain by getting { }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.kotlin.testing.common)
                implementation(libs.bundles.kotest.common)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotest.runner.junit5)
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }
    }

    js(IR) {
        browser()
        nodejs()
        binaries.library()
    }

    val releaseStage: String? by project

    val binarySetup: KotlinNativeTarget.() -> Unit = {
        compilations["main"].defaultSourceSet.dependsOn(sourceSets["nativeMain"])
        compilations["test"].defaultSourceSet.dependsOn(sourceSets["nativeTest"])
        binaries {
            sharedLib()
            staticLib()
            "main".let {
                executable { entryPoint = it }
            }
        }
    }

    when (OperatingSystem.current() to releaseStage.toBoolean()) {
        OperatingSystem.LINUX to false -> configureLinuxCompatiblePlatforms(binarySetup)
        OperatingSystem.WINDOWS to false -> configureWindowsCompatiblePlatforms(binarySetup)
        OperatingSystem.MAC_OS to false -> configureDarwinCompatiblePlatforms(binarySetup)
        OperatingSystem.MAC_OS to true -> configureAllPlatforms(binarySetup)
        else -> throw GradleException(
            "To cross-compile for all the platforms, a `macos` runner should be used"
        )
    }

    targets.all {
        compilations.all {
            kotlinOptions {
                allWarningsAsErrors = true
            }
        }
    }
}

tasks.dokkaJavadoc {
    enabled = false
}

tasks.withType<JavadocJar>().configureEach {
    val dokka = tasks.dokkaHtml.get()
    dependsOn(dokka)
    from(dokka.outputDirectory)
}

signing {
    if (System.getenv("CI") == "true") {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
}

publishOnCentral {
    projectLongName.set("Template for Kotlin Multiplatform Project")
    projectDescription.set("A template repository for Kotlin Multiplatform projects")
    repository("https://maven.pkg.github.com/danysk/${rootProject.name}".toLowerCase()) {
        user.set("DanySK")
        password.set(System.getenv("GITHUB_TOKEN"))
    }
    publishing {
        publications {
            withType<MavenPublication> {
                pom {
                    developers {
                        developer {
                            name.set("Danilo Pianini")
                            email.set("danilo.pianini@gmail.com")
                            url.set("http://www.danilopianini.org/")
                        }
                    }
                }
            }
        }
    }
}

npmPublish {
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            val npmToken: String? by project
            authToken.set(npmToken)
            dry.set(npmToken.isNullOrBlank())
        }
    }
}

publishing {
    publications {
        publications.withType<MavenPublication>().configureEach {
            if ("OSSRH" !in name) {
                artifact(tasks.javadocJar)
            }
        }
    }
}
