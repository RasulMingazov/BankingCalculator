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
            baseName = ":field:impl"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project(":field:api"))
            implementation(project("::core"))

            implementation(libs.androidx.core.ktx)

            implementation(libs.kotlin.serialization)
            implementation(libs.kotlin.immutable.list)

            implementation(libs.decompose)
            implementation(libs.decompose.extensions)
        }
    }
}

android {
    namespace = "com.psychojean.field.impl"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
