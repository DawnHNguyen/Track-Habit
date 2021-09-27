package com.track.buildsrc

import org.gradle.api.JavaVersion

object SdkVersions {
    const val compileSdkVersion = 31
    const val buildToolsVersion = "31.0.0"
    const val minSdkVersion = 23
    const val targetSdkVersion = 31

    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"
}

object Versions {
    val kotlin = "1.5.31"
    const val room = "2.3.0"
    val timber = "4.7.1"

    val appCompat = "1.3.0-alpha01"
    val coreKtx = "1.5.0-alpha01"
    val constraintLayout = "2.0.0-rc1"

    val recyclerview = "1.2.0-alpha05"
}

object Libraries {
    // ROOM
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // TIMBER
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object KotlinLibraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object AndroidLibraries {
    // ANDROID
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
}

object Modules {
    val app = ":app"

    val navigation = ":navigation"

    val common = ":common"

    val remote = ":remote"

    val trackHabit = ":feature:trackhabit"
}

object TestLibraries{

}

object Kapt {

}