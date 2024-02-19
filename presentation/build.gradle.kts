@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.gms)
}

android {
    namespace = "com.eunji.lookatthis.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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

dependencies {

    implementation(project(":domain"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.espresso)

    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    //image
    implementation(libs.glide)
    //hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    //splash
    implementation(libs.splash)
    //paging
    implementation(libs.paging.runtime)
    //swiperefreshlayout
    implementation(libs.swiperefreshlayout)
    //fcm
    implementation(platform(libs.com.google.firebase))
    implementation(libs.firebase.messagesing)
    implementation(libs.firebase.analytics)
}