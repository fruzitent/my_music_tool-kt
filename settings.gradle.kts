// @help: https://docs.gradle.org/current/kotlin-dsl

rootProject.name = "my_music_tool-kt"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

pluginManagement {
  includeBuild("./gradle/build-logic/")
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
