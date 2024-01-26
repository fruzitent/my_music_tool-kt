package org.fruzitent.mymusictool.gradle

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureAndroid() {
  android {
    compileSdkVersion(libs.findVersion("android-sdk-compile").get().requiredVersion.toInt())
    defaultConfig {
      minSdk = libs.findVersion("android-sdk-min").get().requiredVersion.toInt()
      targetSdk = libs.findVersion("android-sdk-target").get().requiredVersion.toInt()
    }
  }
  androidComponents {}
}

private fun Project.android(action: BaseExtension.() -> Unit) =
    extensions.configure<BaseExtension>(action)

private fun Project.androidComponents(action: AndroidComponentsExtension<*, *, *>.() -> Unit) =
    extensions.configure(AndroidComponentsExtension::class.java, action)
