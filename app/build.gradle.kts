plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-kapt")
}

android {
    namespace = "com.telemedconnect.patient"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.telemedconnect.patient"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.zego.inapp.chat.uikit.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation (libs.timber)
    implementation("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:+")
    implementation("com.github.ZEGOCLOUD:zego_inapp_chat_uikit_android:+")
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.guava)
    implementation(libs.libphonenumber)
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

// Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

// Retrofit converter for Kotlinx Serialization
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}