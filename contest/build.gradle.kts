import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kspModule)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinAndroid)
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

    buildFeatures {
        compose = true
    }

    namespace = "com.codeforcesvisualizer.contest"
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.withType<KotlinCompile>().configureEach {
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.com.google.accompanist.flowlayout)
    implementation(libs.com.google.accompanist.webview)
    implementation(libs.com.google.accompanist.swiperefresh)

    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.lifecycle.viewmodel.ktx)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)

    implementation(libs.koin.androidx.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
