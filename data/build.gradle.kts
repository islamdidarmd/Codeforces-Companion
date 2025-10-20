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

    namespace = "com.codeforcesvisualizer.data"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(Libs.lifecycleRuntimeKtx)
    implementation(Libs.lifecycleExtensions)

    implementation(Libs.okhttp)
    implementation(Libs.loggingInterceptor)
    implementation(Libs.retrofit)
    implementation(Libs.converterMoshi)

    implementation(Libs.hiltAndroid)
    implementation(Libs.hiltNavigationCompose)
    kapt(Libs.hiltCompiler)

    testImplementation(Libs.coreTesting)
    testImplementation(Libs.junit)
    // testImplementation(Libs.mockk)

    androidTestImplementation(Libs.androidJunit)
    androidTestImplementation(Libs.espressoCore)
}
