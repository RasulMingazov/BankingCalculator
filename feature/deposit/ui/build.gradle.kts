plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.composeCompiler)
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
            baseName = ":feature:deposit:ui"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project(":feature:deposit:api"))
            implementation(project(":field:ui"))
            implementation(project(":field:api"))
            implementation(project(":core:ui"))

            implementation(libs.kotlin.immutable.list)
            implementation(libs.mvikotlin)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            implementation(libs.androidx.ui)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.material3)
            implementation(libs.decompose.extensions)
            implementation(libs.decompose)
        }
    }
}

android {
    namespace = "com.psychojean.feature.deposit.ui"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
