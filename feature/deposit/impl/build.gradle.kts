plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsKotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = ":feature:deposit:impl"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
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
        }
    }
}

android {
    namespace = "com.psychojean.feature.deposit.impl"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

