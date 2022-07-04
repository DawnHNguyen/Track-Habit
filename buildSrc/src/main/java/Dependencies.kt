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
    val material = "1.6.1"

    val recyclerview = "1.2.0-alpha05"

    val hilt = "2.38.1"

    val nav = "2.3.5"

    val core_version = "1.7.0"

    val coroutines = "1.3.8"

    val lifecycle = "2.3.0-alpha06"

    val firebase_version = "4.3.10"
    val crashlytic_version = "2.8.1"

    val swipe_reveal = "1.4.1"

    val lifecycle_livedata = "2.5.0-alpha02"

    val gson = "2.8.6"
    val retrofit = "2.9.0"
    val loggingIntercepter = "4.10.0"

    val hawk = "2.0.1"

    val junit = "4.+"
    val ext = "1.1.3"
    val espresso = "3.4.0"
}

object Libraries {
    // ROOM
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // TIMBER
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    //HILT
    val daggerHilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    // NOTIFICATION
    val notification = "androidx.core:core-ktx:${Versions.core_version}"

    //FIREBASE
    val firebase = "com.google.gms:google-services:${Versions.firebase_version}"

    // CRASHLYTIC
    val crashlytic = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytic_version}"

}

object KotlinLibraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object AndroidLibraries {
    //Coroutine
    val kotlinCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // ANDROID
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    val material = "com.google.android.material:material:${Versions.material}"

    // NAVIGATION
    val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"

    // SAFE ARGS
    val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}"

    // SWIPE REVEAL RECYCLERVIEW
    val swipeReviewRecyclerview = "com.chauthai.swipereveallayout:swipe-reveal-layout:${Versions.swipe_reveal}"

    //LIFECYCLE LIVEDATA
    val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_livedata}"

    //RETROFIT
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"

    //OKHTTP3
    val loggingIntercepter = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingIntercepter}"

    //Hawk
    val hawk = "com.orhanobut:hawk:${Versions.hawk}"

}

object Modules {
    val navigation = ":navigation"

    val common = ":common"

    val remote = ":remote"

    val trackHabit = ":feature:trackhabit"
}

object TestLibraries{
    val junit = "junit:junit:${Versions.junit}"
    val ext = "androidx.test.ext:junit:${Versions.ext}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

}

object Kapt {

}