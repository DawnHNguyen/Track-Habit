package com.track.buildsrc

object SdkVersions {
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.1"
    const val minSdkVersion = 23
    const val targetSdkVersion = 30
}

object Versions {
    val kotlin = "1.5.20"
}

object Libraries {

}

object KotlinLibraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object AndroidLibraries {

}

object Modules {
    val app = ":app"

    val navigation = ":navigation"

    val common = ":common"

    val remote = ":remote"

    val trackHabit = "::feature:trackhabit"
}

object TestLibraries{

}

object Kapt {

}