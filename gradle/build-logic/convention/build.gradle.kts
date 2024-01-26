// @help: https://docs.gradle.org/current/kotlin-dsl

plugins { `kotlin-dsl` }

dependencies {
  compileOnly(libs.gradle.android)
  compileOnly(libs.gradle.jetbrains.compose)
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
  }
}
