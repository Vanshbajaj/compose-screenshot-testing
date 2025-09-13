pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.11.2"
        id("org.jetbrains.kotlin.android") version "2.1.20"
        id("com.android.compose.screenshot") version "0.0.1-alpha11"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ComposeScreenshotOfficial"
include(":app")
