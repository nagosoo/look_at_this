import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

fun getLocalProperty(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

android {
    namespace = "com.eunji.lookatthis"
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file("../keystore/key_store")
            storePassword = getLocalProperty("KEY_PASSWORD")
            keyAlias = "key0"
            keyPassword = getLocalProperty("STORE_PASSWORD")
        }
    }

    defaultConfig {
        applicationId = "com.eunji.lookatthis"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    kapt {
        correctErrorTypes = true
    }

}

tasks.register("printVersionName") {
    doLast {
        println(android.defaultConfig.versionName)
    }
}

tasks.register("printVersionCode") {
    doLast {
        println(android.defaultConfig.versionCode)
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.espresso)

    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)

    //serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    //hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    //splash
    implementation(libs.splash)
    //paging
    implementation(libs.paging.common)
    implementation(libs.paging.runtime)

    //implementation("androidx.constraintlayout:constraintlayout:2.1.4")

}


