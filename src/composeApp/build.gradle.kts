// @help: https://docs.gradle.org/current/kotlin-dsl

plugins {
  alias(libs.plugins.mymusictool.android.application)
  alias(libs.plugins.mymusictool.jetbrains.kotlin.multiplatform)
}

android { namespace = "org.fruzitent.mymusictool" }

kotlin {
  sourceSets {
    androidMain.dependencies { implementation(libs.androidx.activity.compose) }
    commonMain.dependencies {
      implementation(libs.androidx.compose.material3)
      implementation(libs.androidx.compose.runtime)
    }
  }
}
