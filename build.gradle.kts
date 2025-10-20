import org.gradle.api.tasks.Delete

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Classpaths.androidGradle)
        classpath(Classpaths.kotlinGradle)
        classpath(Classpaths.hiltGradle)
        classpath(Classpaths.googleServices)
        classpath(Classpaths.firebaseCrashlytics)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
