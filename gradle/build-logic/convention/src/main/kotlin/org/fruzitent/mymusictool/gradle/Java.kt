package org.fruzitent.mymusictool.gradle

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure

fun Project.configureJava() {
  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(libs.findVersion("jdk").get().requiredVersion))
    }
  }
}

private fun Project.java(action: JavaPluginExtension.() -> Unit) {
  extensions.configure<JavaPluginExtension>(action)
}
