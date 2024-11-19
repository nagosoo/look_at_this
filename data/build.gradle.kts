@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.eunji.lookatthis.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "baseUrl", "\"http://192.168.0.104:8080\"")
        }

        getByName("release") {
            buildConfigField(
                "String",
                "baseUrl",
                "\"http://34.64.93.230:8080\""
            )
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
        buildConfig = true
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.espresso)

    //retrofit
    implementation(libs.retrofit)
    //okhttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    //serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    //datastore
    implementation(libs.datastore)
    //EncryptedSharedPreferences
    implementation(libs.security.crypto.ktx)
    //hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    //paging
    implementation(libs.paging.runtime)
}
