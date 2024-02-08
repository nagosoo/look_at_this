plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")
}

android {
    namespace = "com.eunji.lookatthis"
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file("../keystore/key_store")
            storePassword = file("../keystore/password.rtf").readText().trim()
            keyAlias = "key0"
            keyPassword = file("../keystore/password.rtf").readText().trim()
        }
    }

    defaultConfig {
        applicationId = "com.eunji.lookatthis"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        getByName("debug"){
            buildConfigField("String", "baseUrl", "\"http://192.168.0.110:8080\"")
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            buildConfigField("String", "baseUrl", "\"http://lookatthisbe-env.eba-mmt8camh.ap-northeast-2.elasticbeanstalk.com\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ViewModel
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    //image
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //fcm
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    //okhttp logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    //dataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //EncryptedSharedPreferences
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")

    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //ktx
    implementation ("androidx.activity:activity-ktx:1.8.2")

    //dataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //splash
    implementation ("androidx.core:core-splashscreen:1.0.1")

    //paging
    implementation("androidx.paging:paging-common:3.2.1")
    implementation("androidx.paging:paging-runtime:3.2.1")

}

kapt {
    correctErrorTypes = true
}


