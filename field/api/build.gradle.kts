plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":core"))

    implementation(libs.decompose)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.immutable.list)

    compileOnly(libs.compose.stable.marker)
}
