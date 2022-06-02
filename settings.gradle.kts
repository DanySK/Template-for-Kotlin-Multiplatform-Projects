plugins {
    id("com.gradle.enterprise") version "3.10.1"
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.0.11"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishOnFailure()
    }
}

gitHooks {
    commitMsg { conventionalCommits() }
    createHooks()
}

rootProject.name = "Template-for-Kotlin-JVM-Projects"
enableFeaturePreview("VERSION_CATALOGS")
