plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
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

    namespace = "com.codeforcesvisualizer.compare"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(Libs.composeUi)
    implementation(Libs.composeFoundation)
    implementation(Libs.composeMaterial)

    implementation(Libs.composeMaterialIconsCore)
    implementation(Libs.composeMaterialIconsExtended)

    implementation(Libs.viewModelCompose)

    implementation(Libs.composeUiToolingPreview)
    debugImplementation(Libs.composeUiTooling)

    implementation(Libs.mpAndroidChart)

    implementation(Libs.hiltAndroid)
    implementation(Libs.hiltNavigationCompose)
    kapt(Libs.hiltCompiler)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidJunit)
    androidTestImplementation(Libs.espressoCore)
}
