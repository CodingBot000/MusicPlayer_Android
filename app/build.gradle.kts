plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.9.22"
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sample.myplayer"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    namespace = "com.sample.myplayer"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.activity.compose)
    implementation(libs.constraintlayout.compose)

    implementation(libs.navigation.compose)
    // ExoPlayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.session)

    implementation(libs.lifecycle.extensions)
    implementation(libs.bundles.lifecycle)

    implementation(libs.bundles.coroutines)

    implementation(libs.coil)

    implementation(libs.bundles.accompanist)

    // Dagger - Hilt
    implementation(libs.bundles.hilt)
    implementation(libs.media3.ui)
    kapt(libs.bundles.hilt.kapt)

    implementation(libs.com.google.code.gson)

    implementation(libs.squareup.okhttp3.okhttp)
    implementation(libs.squareup.okhttp3.loggin.interceptor)
}