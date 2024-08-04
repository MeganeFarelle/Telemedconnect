// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    val kotlinVersion by extra("1.9.0")
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}