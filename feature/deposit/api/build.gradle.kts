plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
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
            baseName = ":feature:deposit:api"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project(":core"))
            implementation(project(":field:api"))

            implementation(libs.decompose)
            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.immutable.list)
            implementation(libs.mvikotlin)
        }
    }
}

android {
    namespace = "com.psychojean.feature.deposit.api"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

