rootProject.name = "maplibre-gl-android-examples"

include(":app")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.0.2"
        id("com.android.library") version "8.0.2"
        id("org.jetbrains.kotlin.android") version "1.8.22"
    }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}