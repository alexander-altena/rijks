plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID) // or kotlin("android") or id 'kotlin-android'
    id(Plugins.KOTLIN_KAPT)  // or kotlin("kapt")
    id(Plugins.DAGGER_HILT)
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

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }

    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

    }

    buildFeatures {

        dataBinding = true

        // for view binding:
        // viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    dynamicFeatures = mutableSetOf(":features:rijksstudio")
}

dependencies {

    //Core
    api (Dependencies.ANDROID_MATERIAL)
    api (Dependencies.ANDROID_X_FRAGMENT_KTX)
    api (Dependencies.ANDROID_X_APPCOMPAT)
    api (Dependencies.ANDROID_X_LEGACY)

    //Kotlin
    api (Dependencies.KOTLIN)
    api (Dependencies.KOTLIN_COROUTINES)
    api (Dependencies.KOTLIN_COROUTINES_ANDROID)

    //Network
    api (Dependencies.RETROFIT)
    api (Dependencies.RETROFIT_MOSHI_CONVERTER)
    api (Dependencies.MOSHI)
    kapt (Dependencies.MOSHI_KAPT)

    //Room
    api (Dependencies.ANDROID_X_ROOM)
    api (Dependencies.ANDROID_X_ROOM_KTX)
    kapt (Dependencies.ANDROID_X_ROOM_KAPT)

    //Navigation
    api(Dependencies.ANDROID_X_NAV_KTX)
    api(Dependencies.ANDROID_X_NAV_FRAGMENT_KTX)
    api(Dependencies.ANDROID_X_NAV_DYNAMIC)

    //DI
    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_KAPT)

    //Paging
    api(Dependencies.ANDROID_X_PAGING)

    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.jetbrains.kotlin:kotlin-test-junit:1.4.21")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")

    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

}