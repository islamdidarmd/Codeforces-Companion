plugins {
    alias(libs.plugins.kspModule)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinAndroid)
}
android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.codeforcesvisualizer"
        minSdk = libs.versions.minSdk.get().toInt()
        
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
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

    namespace = "com.codeforcesvisualizer"
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":core"))
    implementation(project(":shared"))
    implementation(project(":contest"))
    implementation(project(":webview"))
    implementation(project(":profile"))
    implementation(project(":compare"))
    implementation(project(":preference"))

    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.compose.ui)
    implementation(libs.com.google.android.material)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.com.google.accompanist.systemuicontroller)
    implementation(libs.io.ktor.client.core)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
}
