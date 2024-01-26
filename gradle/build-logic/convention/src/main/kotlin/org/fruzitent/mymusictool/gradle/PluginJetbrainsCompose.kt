package org.fruzitent.mymusictool.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension

class PluginJetbrainsCompose : Plugin<Project> {
  override fun apply(target: Project) = with(target) { configureJetbrainsCompose() }
}

fun Project.configureJetbrainsCompose() {
  with(pluginManager) { apply("org.jetbrains.compose") }
  compose {}
}

private fun Project.compose(action: ComposeExtension.() -> Unit) {
  extensions.configure<ComposeExtension>(action)
}
