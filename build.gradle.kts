@file:Suppress("UnstableApiUsage")

import org.danilopianini.gradle.mavencentral.JavadocJar
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly

//buildscript {
//    dependencies {
//        classpath("com.android.tools.build:gradle:7.0.0")
//    }
//}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
//    id("com.quittle.setup-android-sdk") version "3.0.0"
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.publishOnCentral)
    alias(libs.plugins.taskTree)
}

group = "org.danilopianini"

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/res/AndroidManifest.xml")
    buildToolsVersion = "30.0.2"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
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
        jvmToolchain {
            // TODO
        }
    }
    js(BOTH) {
        browser()
        nodejs()
    }
    android()
//    androidNativeArm32()
//    androidNativeArm64()
//    androidNativeX86()
//    androidNativeX64()
//    iosArm32()
//    iosArm64()
//    iosX64()
//    iosSimulatorArm64()
//    watchosArm32()
//    watchosArm64()
//    watchosX86()
//    watchosX64()
//    watchosSimulatorArm64()
//    tvosArm64()
//    tvosX64()
//    tvosSimulatorArm64()
//    wasm32()
    val hostOs = System.getProperty("os.name").trim().toLowerCaseAsciiOnly()
    val hostArch = System.getProperty("os.arch").trim().toLowerCaseAsciiOnly()
    val nativeTarget: (String, org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit) -> KotlinTarget =
        when (hostOs to hostArch) {
            "linux" to "aarch64" -> ::linuxArm64
            "linux" to "amd64" -> ::linuxX64
            "linux" to "arm", "linux" to "arm32" -> ::linuxArm32Hfp
            "linux" to "mips", "linux" to "mips32" -> ::linuxMips32
            "linux" to "mipsel", "linux" to "mips32el" -> ::linuxMipsel32
            "mac os x" to "aarch64" -> ::macosArm64
            "mac os x" to "amd64", "mac os x" to "x86_64" -> ::macosX64
            "windows" to "amd64" -> ::mingwX64
            "windows" to "x86" -> ::mingwX86
            else -> throw GradleException("Host OS $hostOs with arch $hostArch is not supported in Kotlin/Native.")
        }
    nativeTarget("native") {
        binaries {
            sharedLib()
            staticLib()
            // Disable if there is no executable
            executable()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val commonJvmMain by creating {
            dependsOn(commonMain)
        }
        val commonJvmTest by creating {
            dependsOn(commonJvmMain)
            dependsOn(commonTest)
        }
        val jvmMain by getting {
            dependsOn(commonJvmMain)
        }
        val jvmTest by getting {
            dependsOn(commonJvmTest)
        }
        val androidMain by getting {
            dependsOn(commonJvmMain)
        }
        val androidTest by getting {
            dependsOn(commonJvmTest)
        }
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting {
            dependsOn(commonMain)
        }
        val nativeTest by getting {
            dependsOn(commonTest)
        }
    }
    targets.all {
        compilations.all {
            kotlinOptions {
                allWarningsAsErrors = true
            }
        }
    }
}

tasks.register<Jar>("jar")

tasks.dokkaJavadoc {
    enabled = false
}
tasks.withType<JavadocJar> {
    dependsOn(tasks.dokkaHtml.get())
    from(tasks.dokkaHtml.get().outputDirectory)
}

signing {
    if (System.getenv("CI") == "true") {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
}

publishOnCentral {
    projectLongName.set("Template Kotlin JVM Project")
    projectDescription.set("A template repository for Kotlin JVM projects")
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
