plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.nikhiljain.blockiesgenerator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nikhiljain.blockiesgenerator"
        minSdk = 19
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Glide dependency for :blockiesglide module
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // for API 20 or lower
    implementation("androidx.multidex:multidex:2.0.1")

    // BlockiesView dependency
//    implementation("com.github.nikhiljainlive.blockies_android:blockiesview:v0.2.1")
    implementation(project(":blockiesview"))        // TODO: update this with jitpack dependency

    // BlockiesGenerator dependency
//    implementation("com.github.nikhiljainlive.blockies_android:blockiesgenerator:v0.2.1")
    implementation(project(":blockiesgenerator"))   // TODO: update this with jitpack dependency

    // BlockiesGlide dependency
//    implementation("com.github.nikhiljainlive.blockies_android:blockiesglide:v0.2.1")
    implementation(project(":blockiesglide"))       // TODO: update this with jitpack dependency

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}