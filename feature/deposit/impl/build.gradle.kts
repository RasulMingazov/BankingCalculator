plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinSerialization)
}

android {
    namespace = "com.psychojean.feature.deposit.impl"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":feature:deposit:api"))
    implementation(project(":field:api"))
    implementation(project("::core"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlin.immutable.list)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.coroutines)
    implementation(libs.mvikotlin.main)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}