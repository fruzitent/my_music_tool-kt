// @help: https://docs.gradle.org/current/kotlin-dsl

rootProject.name = "build-logic"

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
  versionCatalogs { create("libs") { from(files("../libs.versions.toml")) } }
}

include(":convention")
