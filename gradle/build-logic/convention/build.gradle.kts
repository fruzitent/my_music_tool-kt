// @help: https://docs.gradle.org/current/kotlin-dsl

plugins {
  // TODO: Gradle v9.0 ft. Kotlin 2.0
  id("org.gradle.kotlin.kotlin-dsl") version "4.3.1"
}

dependencies {
  compileOnly(libs.gradle.android)
  compileOnly(libs.gradle.jetbrains.compose)
  compileOnly(libs.gradle.jetbrains.kotlin)
}

gradlePlugin {
  plugins {
    register("AndroidApplication") {
      id = "org.fruzitent.mymusictool.android.application"
      implementationClass = "org.fruzitent.mymusictool.gradle.PluginAndroidApplication"
    }
    register("AndroidLibrary") {
      id = "org.fruzitent.mymusictool.android.library"
      implementationClass = "org.fruzitent.mymusictool.gradle.PluginAndroidLibrary"
    }
    register("JetbrainsCompose") {
      id = "org.fruzitent.mymusictool.jetbrains.compose"
      implementationClass = "org.fruzitent.mymusictool.gradle.PluginJetbrainsCompose"
    }
    register("JetbrainsKotlinMultiplatform") {
      id = "org.fruzitent.mymusictool.jetbrains.kotlin.multiplatform"
      implementationClass = "org.fruzitent.mymusictool.gradle.PluginJetbrainsKotlinMultiplatform"
    }
  }
}
