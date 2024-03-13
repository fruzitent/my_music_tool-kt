// @help: https://docs.gradle.org/current/kotlin-dsl

rootProject.name = "build-logic"

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
  versionCatalogs { create("libs") { from(files("../libs.versions.toml")) } }
}

pluginManagement {
  repositories {
    // TODO: Gradle v9.0 ft. Kotlin 2.0
    maven(url = "./.m2/repository/")
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

include(":convention")
