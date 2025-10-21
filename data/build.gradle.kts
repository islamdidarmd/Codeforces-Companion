plugins {
    alias(libs.plugins.kspModule)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.serialization)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    namespace = "com.codeforcesvisualizer.data"
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)

    implementation(libs.io.ktor.client.core)
    implementation(libs.io.ktor.client.cio)
    implementation(libs.io.ktor.client.logging)
    implementation(libs.io.ktor.client.content.negotiation)
    implementation(libs.io.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.junit)
    // testImplementation(libs.io.mockk)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
