package org.fruzitent.mymusictool.gradle

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import java.io.FileInputStream
import java.util.Properties
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

fun Project.configureKeystore() {
  val keystorePropertiesFile = file("${project.rootDir}/jks/keystore.properties")
  val keystoreProperties = Properties()

  if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
  } else {
    keystoreProperties["keyAlias"] = System.getenv("KEYALIAS") ?: error("KEYALIAS is not set")
    keystoreProperties["keyPassword"] = System.getenv("KEYPWD") ?: error("KEYPWD is not set")
    keystoreProperties["storeFile"] = System.getenv("KSTOREFILE") ?: error("KSTOREFILE is not set")
    keystoreProperties["storePassword"] = System.getenv("KSTOREPWD") ?: error("KSTOREPWD is not set")
  }

  android {
    signingConfigs {
      create("release") {
        keyAlias = keystoreProperties.getProperty("keyAlias")
        keyPassword = keystoreProperties.getProperty("keyPassword")
        storeFile = file("${project.rootDir}/${keystoreProperties.getProperty("storeFile")}")
        storePassword = keystoreProperties.getProperty("storePassword")
      }
    }
    buildTypes {
      getByName("release") {
        signingConfig = signingConfigs.getByName("release")
      }
    }
  }
}

fun Project.configureNDK() {
  android {
    defaultConfig {
      ndk {
        abiFilters += listOf("arm64-v8a", "x86_64")
      }
    }
  }
}

private fun Project.android(action: BaseExtension.() -> Unit) {
  extensions.configure<BaseExtension>(action)
}

private fun Project.androidComponents(action: AndroidComponentsExtension<*, *, *>.() -> Unit) {
  extensions.configure(AndroidComponentsExtension::class.java, action)
}
