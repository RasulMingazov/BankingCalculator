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
            baseName = ":root:impl"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project(":root:api"))

            implementation(project(":feature:deposit:api"))
            implementation(project(":feature:deposit:ui"))

            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            implementation(libs.androidx.ui)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.material3)

            implementation(libs.decompose)
            implementation(libs.decompose.extensions)
        }
    }
}

android {
    namespace = "com.psychojean.root.impl"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
