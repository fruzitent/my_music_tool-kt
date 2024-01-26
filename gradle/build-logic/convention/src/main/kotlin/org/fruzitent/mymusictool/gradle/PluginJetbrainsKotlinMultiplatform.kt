package org.fruzitent.mymusictool.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class PluginJetbrainsKotlinMultiplatform : Plugin<Project> {
  override fun apply(target: Project) = with(target) { configureJetbrainsKotlinMultiplatform() }
}

fun Project.configureJetbrainsKotlinMultiplatform() {
  with(pluginManager) { apply("org.jetbrains.kotlin.multiplatform") }
  kotlin { androidTarget {} }
  configureKotlin()
}

private fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) {
  extensions.configure<KotlinMultiplatformExtension>(action)
}
