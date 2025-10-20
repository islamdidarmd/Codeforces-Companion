import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

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

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    namespace = "com.codeforcesvisualizer.core"
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(Libs.composeUi)
    implementation(Libs.composeMaterial)
    implementation(Libs.coilCompose)

    implementation(Libs.mpAndroidChart)

    implementation(Libs.composeRuntimeLiveData)

    implementation(Libs.activityCompose)
    implementation(Libs.viewModelCompose)

    implementation(Libs.composeUiToolingPreview)
    debugImplementation(Libs.composeUiTooling)

    api(platform(Libs.firebaseBom))
    api(Libs.crashlytics)
    api(Libs.analytics)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidJunit)
    androidTestImplementation(Libs.espressoCore)
}
