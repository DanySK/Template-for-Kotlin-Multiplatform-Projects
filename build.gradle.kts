@file:Suppress("UnstableApiUsage")

import org.danilopianini.gradle.mavencentral.JavadocJar
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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

val binarySetup: KotlinNativeTarget.() -> Unit = {
    binaries {
        sharedLib()
        staticLib()
        "main".let {
            executable { entryPoint = it }
        }
    }
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

    js(IR) {
        browser()
        nodejs()
        binaries.library()
    }

    linuxX64(binarySetup)
    linuxArm64(binarySetup)
    linuxArm32Hfp(binarySetup)

    mingwX64(binarySetup)
    mingwX86(binarySetup)

    macosX64(binarySetup)
    macosArm64(binarySetup)

    ios(binarySetup)
    iosArm32(binarySetup)
    iosSimulatorArm64(binarySetup)
    tvos(binarySetup)
    tvosSimulatorArm64(binarySetup)
    watchos(binarySetup)
    watchosX86(binarySetup)
    watchosSimulatorArm64(binarySetup)

    sourceSets {
        // Main source sets
        val commonMain by getting { }

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val macosX64Main by getting { dependsOn(nativeMain) }
        val macosArm64Main by getting { dependsOn(nativeMain) }

        val mingwX64Main by getting { dependsOn(nativeMain) }
        val mingwX86Main by getting { dependsOn(nativeMain) }

        val linuxX64Main by getting { dependsOn(nativeMain) }
        val linuxArm64Main by getting { dependsOn(nativeMain) }
        val linuxArm32HfpMain by getting { dependsOn(nativeMain) }

        val iosX64Main by getting { dependsOn(nativeMain) }
        val iosArm64Main by getting { dependsOn(nativeMain) }
        val iosArm32Main by getting { dependsOn(nativeMain) }
        val iosSimulatorArm64Main by getting { dependsOn(nativeMain) }

        val watchosArm32Main by getting { dependsOn(nativeMain) }
        val watchosArm64Main by getting { dependsOn(nativeMain) }
        val watchosX86Main by getting { dependsOn(nativeMain) }
        val watchosX64Main by getting { dependsOn(nativeMain) }
        val watchosSimulatorArm64Main by getting { dependsOn(nativeMain) }

        val tvosMain by getting { dependsOn(nativeMain) }
        val tvosSimulatorArm64Main by getting { dependsOn(nativeMain) }

        // Test sourcesets
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.kotlin.testing.common)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.bundles.kotest.common)
                implementation(libs.kotest.runner.junit5)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(libs.bundles.kotest.common)
            }
        }
        val nativeTest by creating {
            dependsOn(commonTest)
            dependencies {
                implementation(libs.bundles.kotest.common)
            }
        }

        val macosX64Test by getting { dependsOn(nativeTest) }
        val macosArm64Test by getting { dependsOn(nativeTest) }

        val mingwX64Test by getting { dependsOn(nativeTest) }

        val linuxX64Test by getting { dependsOn(nativeTest) }

        val iosX64Test by getting { dependsOn(nativeTest) }
        val iosArm64Test by getting { dependsOn(nativeTest) }
        val iosArm32Test by getting { dependsOn(nativeTest) }
        val iosSimulatorArm64Test by getting { dependsOn(nativeTest) }

        val watchosArm32Test by getting { dependsOn(nativeTest) }
        val watchosArm64Test by getting { dependsOn(nativeTest) }
        val watchosX86Test by getting { dependsOn(nativeTest) }
        val watchosX64Test by getting { dependsOn(nativeTest) }
        val watchosSimulatorArm64Test by getting { dependsOn(nativeTest) }

        val tvosTest by getting { dependsOn(nativeTest) }
        val tvosSimulatorArm64Test by getting { dependsOn(nativeTest) }
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
