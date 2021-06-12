object Dependencies {

    //Gradle classpath
    const val ANDROID_TOOLS_GRADLE = "com.android.tools.build:gradle:${Versions.BUILD_TOOLS_VERSION}"
    const val KOTLIN_TOOLS_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}"
    const val DAGGER_HILT_GRADLE = "com.google.dagger:hilt-android-gradle-plugin:${Versions.DAGGER_HILT_VERSION}"


    //Core
    const val ANDROID_MATERIAL = "com.google.android.material:material:${Versions.ANDROID_MATERIAL_VERSION}"
    const val ANDROID_X_FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.ANDROID_X_FRAGMENT_KTX_VERSION}"
    const val ANDROID_X_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.ANDROID_X_APPCOMPAT_VERSION}"
    const val ANDROID_X_LEGACY = "androidx.legacy:legacy-support-v4:${Versions.ANDROID_X_LEGACY_VERSION}"

    // ViewModel
    const val ANDROID_X_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROID_X_LIFECYCLE_VERSION}"
    const val ANDROID_X_LIFECYCLE = "androidx.lifecycle:lifecycle-common-java8:${Versions.ANDROID_X_LIFECYCLE_VERSION}"

    //Room
    const val ANDROID_X_ROOM = "androidx.room:room-runtime:${Versions.ANDROID_X_ROOM_VERSION}"
    const val ANDROID_X_ROOM_KTX = "androidx.room:room-ktx:${Versions.ANDROID_X_ROOM_VERSION}"
    const val ANDROID_X_ROOM_KAPT = "androidx.room:room-compiler:${Versions.ANDROID_X_ROOM_VERSION}"

    //Navigation
    const val ANDROID_X_NAV_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAV_VERSION}"
    const val ANDROID_X_NAV_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAV_VERSION}"
    const val ANDROID_X_NAV_DYNAMIC = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.NAV_VERSION}"

    // Paging
    const val ANDROID_X_PAGING = "androidx.paging:paging-runtime-ktx:${Versions.ANDROID_X_PAGING_VERSION}"

    //kotlin
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"
    const val KOTLIN_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES_VERSION}"
    const val KOTLIN_COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_COROUTINES_VERSION}"

    //Network
    const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI_VERSION}"
    const val MOSHI_KAPT = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI_VERSION}"
    const val RETROFIT_MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"

    //Dependency Injection
    const val DAGGER_HILT = "com.google.dagger:hilt-android:${Versions.DAGGER_HILT_VERSION}"
    const val DAGGER_HILT_KAPT = "com.google.dagger:hilt-android-compiler:${Versions.DAGGER_HILT_VERSION}"

}