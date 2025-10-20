plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
    }

    buildTypes {
        debug {
            /*
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            */
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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

    namespace = "com.codeforcesvisualizer"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":contest"))
    implementation(project(":webview"))
    implementation(project(":profile"))
    implementation(project(":compare"))
    implementation(project(":preference"))

    implementation(Libs.appCompat)

    implementation(Libs.composeUi)
    implementation(Libs.material)
    implementation(Libs.composeMaterial)
    implementation(Libs.composeMaterialIconsCore)
    implementation(Libs.composeMaterialIconsExtended)

    implementation(Libs.composeNavigation)

    implementation(Libs.composeUiToolingPreview)
    debugImplementation(Libs.composeUiTooling)

    implementation(Libs.composeAccompanistSystemUiController)

    implementation(Libs.okhttp)
    implementation(Libs.retrofit)

    implementation(Libs.hiltAndroid)
    implementation(Libs.hiltNavigationCompose)
    kapt(Libs.hiltCompiler)
}
