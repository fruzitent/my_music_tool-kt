package org.fruzitent.mymusictool.gradle

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import java.io.File
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

private val Project.keystoreProperties: Lazy<Properties?>
  get() = lazy {
    val keystorePropertiesFile = rootProject.file("./jks/keystore.properties")
    if (keystorePropertiesFile.exists()) {
      Properties().apply { load(FileInputStream(keystorePropertiesFile)) }
    } else {
      null
    }
  }

private fun Project.getProperty(key: String, name: String): String {
  return keystoreProperties.value?.getProperty(key)
    ?: System.getenv(name)
    ?: error("${name} is not set")
}

private fun Project.getStoreFile(pathname: String): File {
  return if (File(pathname).isAbsolute) {
    file(pathname)
  } else {
    file("${project.rootDir}/${pathname}")
  }
}

fun Project.configureKeystore() {
  android {
    buildTypes {
      getByName("release") {
        isMinifyEnabled = true
        signingConfig = signingConfigs.create("release")
      }
    }
    signingConfigs {
      if (keystoreProperties.value != null) {
        getByName("release") {
          keyAlias = getProperty("keyAlias", "KEYALIAS")
          keyPassword = getProperty("keyPassword", "KEYPWD")
          storeFile = getStoreFile(getProperty("storeFile", "KSTOREFILE"))
          storePassword = getProperty("storePassword", "KSTOREPWD")
        }
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
