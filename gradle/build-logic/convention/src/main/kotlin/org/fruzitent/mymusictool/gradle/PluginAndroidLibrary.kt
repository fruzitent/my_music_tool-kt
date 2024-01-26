package org.fruzitent.mymusictool.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginAndroidLibrary : Plugin<Project> {
  override fun apply(target: Project) = with(target) { configureAndroidLibrary() }
}

fun Project.configureAndroidLibrary() {
  with(pluginManager) { apply("com.android.library") }
  configureAndroid()
}
