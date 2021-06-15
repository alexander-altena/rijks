
plugins {
    id(Plugins.ANDROID_DYNAMIC_FEATURE)
    id(Plugins.KOTLIN_ANDROID) // or kotlin("android") or id 'kotlin-android'
    id(Plugins.KOTLIN_KAPT)  // or kotlin("kapt")
    id(Plugins.DAGGER_HILT)
    id(Plugins.SAFE_ARGS)
}
android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)

        testOptions {
            unitTests.isIncludeAndroidResources = true
        }

    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

    }

    dataBinding.isEnabled = true

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

    //viewModel
    implementation(Dependencies.ANDROID_X_VIEWMODEL)


    kapt (Dependencies.ANDROID_X_ROOM_KAPT)

    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_KAPT)

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.5.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")


    androidTestImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("org.jetbrains.kotlin:kotlin-test-junit:1.5.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation ("androidx.annotation:annotation:1.2.0")
    androidTestImplementation ("androidx.test:rules:1.3.0")
    androidTestImplementation ("org.mockito:mockito-core:2.21.0")
    androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.3.0")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.28-alpha")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.35")

    debugImplementation ("androidx.fragment:fragment-testing:1.4.0-alpha02")
}