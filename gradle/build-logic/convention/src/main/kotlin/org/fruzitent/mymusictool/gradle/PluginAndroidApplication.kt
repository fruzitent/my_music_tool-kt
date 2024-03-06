package org.fruzitent.mymusictool.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginAndroidApplication : Plugin<Project> {
  override fun apply(target: Project) = with(target) { configureAndroidApplication() }
}

fun Project.configureAndroidApplication() {
  with(pluginManager) { apply("com.android.application") }
  configureAndroid()
  configureKeystore()
  configureNDK()
}
