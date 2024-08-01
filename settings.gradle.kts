rootProject.name = "maplibre-gl-android-examples"

include(":app")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.5.1"
        id("com.android.library") version "8.5.1"
        id("org.jetbrains.kotlin.android") version "1.9.23"
    }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}