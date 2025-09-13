plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.screenshot) // official Compose screenshot plugin
}

android {
    namespace = "com.example.composescreenshotofficial"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.composescreenshotofficial"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    // enable experimental flag for the plugin
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // screenshot validation API
   // screenshotTestImplementation(libs.screenshot.validation.api)

    testImplementation(libs.junit)
}
