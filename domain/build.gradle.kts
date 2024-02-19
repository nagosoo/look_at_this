@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlinx.courutines.core)
    implementation(libs.paging.common)
    implementation(libs.javax.inject)
}
