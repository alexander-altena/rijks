plugins {
    id(Plugins.ANDROID_DYNAMIC_FEATURE)
    id(Plugins.KOTLIN_ANDROID) // or kotlin("android") or id 'kotlin-android'
    id(Plugins.KOTLIN_KAPT)  // or kotlin("kapt")
    id(Plugins.DAGGER_HILT)
    id("kotlin-android")
}
android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = AndroidConfig.ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(project(ModuleDependency.APP))

    //Core
    implementation (Dependencies.ANDROID_MATERIAL)
    implementation (Dependencies.ANDROID_X_FRAGMENT_KTX)
    implementation (Dependencies.ANDROID_X_APPCOMPAT)
    implementation (Dependencies.ANDROID_X_LEGACY)

    //Kotlin
    implementation (Dependencies.KOTLIN)
    implementation (Dependencies.KOTLIN_COROUTINES)
    implementation (Dependencies.KOTLIN_COROUTINES_ANDROID)

    //viewModel
    implementation(Dependencies.ANDROID_X_VIEWMODEL)

    //Room
    implementation (Dependencies.ANDROID_X_ROOM)
    implementation (Dependencies.ANDROID_X_ROOM_KTX)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt (Dependencies.ANDROID_X_ROOM_KAPT)

    //DI
    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_KAPT)

    //Paging
    implementation(Dependencies.ANDROID_X_PAGING)

    //Retrofit
    implementation (Dependencies.RETROFIT)

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.4.21")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")

    androidTestImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("org.jetbrains.kotlin:kotlin-test-junit:1.4.21")
    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation ("androidx.annotation:annotation:1.1.0")
}