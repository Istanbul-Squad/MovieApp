import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())
val apiKey: String = localProperties.getProperty("apiKey") ?: ""

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.safeargs)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.karrar.movieapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.karrar.movieapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        buildConfigField ("String", "API_KEY", apiKey)
        buildConfigField ("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField ("String", "IMAGE_BASE_PATH", "\"https://image.tmdb.org/t/p/w500\"")
        buildConfigField ("String", "TMDB_SIGNUP_URL", "\"https://www.themoviedb.org/signup\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin{
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("11")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

}

dependencies {
    // Android Dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size.class1)

    // Third-Party Libraries
    implementation(libs.lottie)
    implementation(libs.coil)
    implementation(libs.picasso)
    implementation(libs.youtube.player)
    implementation(libs.expandabletextview)

    hiltDependencies()
    roomDependencies()
    pagingDependencies()
    retrofitDependencies()
    testingDependencies()
    uiDependencies()
    dataStoreDependencies()
    lifecycleDependencies()
}

private fun DependencyHandlerScope.testingDependencies() {
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

private fun DependencyHandlerScope.uiDependencies() {
    implementation(libs.androidx.material)
    implementation(libs.androidx.recyclerview)
}

private fun DependencyHandlerScope.lifecycleDependencies() {
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}

private fun DependencyHandlerScope.dataStoreDependencies() {
    implementation(libs.androidx.datastore.preferences)
}

fun DependencyHandlerScope.hiltDependencies() {
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.javax.inject)
}

private fun DependencyHandlerScope.roomDependencies() {
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}

private fun DependencyHandlerScope.pagingDependencies(){
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.runtime.ktx)
}

private fun DependencyHandlerScope.retrofitDependencies(){
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
}