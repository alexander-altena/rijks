object AndroidConfig {
    const val COMPILE_SDK_VERSION = 30
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 30

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val ID = "com.example.rijks"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
}

