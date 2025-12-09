plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "kr.ac.dankook.autoinfo"
    compileSdk = 36

    defaultConfig {
        applicationId = "kr.ac.dankook.autoinfo"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)


    // 프로젝트에 명시적으로 필요한 라이브러리들
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))
    // Firebase (필요시 google-services 플러그인 및 프로젝트 레벨 설정 확인)
    implementation("com.google.firebase:firebase-auth:22.1.1")
    implementation("com.google.firebase:firebase-firestore:24.7.0")
    implementation("com.google.firebase:firebase-analytics:21.3.0")
    // === 여기부터 복붙: build.gradle.kts dependencies 블록 안에 추가 ===
    implementation("com.google.firebase:firebase-firestore")
    // === 여기까지 복붙 ===


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}