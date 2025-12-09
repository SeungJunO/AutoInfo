// ⬇⬇⬇ [복붙 1] : 프로젝트 루트 build.gradle.kts 전체 내용을 이걸로 교체

plugins {
    // 안드로이드 앱 플러그인
    id("com.android.application") version "8.13.1" apply false

    // 코틀린 안드로이드 플러그인
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false

    // Firebase Google Services 플러그인
    id("com.google.gms.google-services") version "4.4.2" apply false
}
