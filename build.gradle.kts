// @help: https://docs.gradle.org/current/kotlin-dsl

plugins {
  alias(libs.plugins.android.application).apply(false)
  alias(libs.plugins.android.library).apply(false)
  alias(libs.plugins.jetbrains.compose).apply(false)
  alias(libs.plugins.jetbrains.kotlin.multiplatform).apply(false)
}
