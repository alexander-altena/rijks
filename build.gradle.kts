// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath (Dependencies.ANDROID_TOOLS_GRADLE)
        classpath (Dependencies.KOTLIN_TOOLS_GRADLE)
        classpath(Dependencies.DAGGER_HILT_GRADLE)
        classpath(Dependencies.SAFE_ARGS_GRADLE)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}