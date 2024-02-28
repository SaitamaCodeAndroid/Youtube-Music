plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.learnbyheart.feature.home"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
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
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    implementation(platform("androidx.compose:compose-bom:2024.02.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Constraint layout
    // https://developer.android.com/jetpack/androidx/releases/constraintlayout
    val layoutVersion = "1.0.1"
    implementation("androidx.constraintlayout:constraintlayout-compose:$layoutVersion")

    // Pull to refresh
    // https://developer.android.com/jetpack/androidx/releases/compose-material
    implementation("androidx.compose.material:material:1.6.2")

    // Lifecycle aware
    // https://developer.android.com/jetpack/androidx/releases/lifecycle
    val lifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    // Hilt
    // https://developer.android.com/jetpack/androidx/releases/hilt?hl=vi
    val hiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Navigation Component
    // https://developer.android.com/jetpack/androidx/releases/navigation
    // Jetpack Compose Integration
    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Media3
    // https://developer.android.com/jetpack/androidx/releases/media3?hl=vi
    val media3Version = "1.2.1"
    implementation("androidx.media3:media3-exoplayer:$media3Version")

    // Paging
    // https://developer.android.com/jetpack/androidx/releases/paging
    // Jetpack Compose integration
    val pagingVersion = "3.3.0-alpha03"
    implementation("androidx.paging:paging-compose:$pagingVersion")

    // Navigation with hilt
    // https://developer.android.com/jetpack/androidx/releases/hilt
    val hiltComposeVersion = "1.2.0"
    implementation("androidx.hilt:hilt-navigation-compose:$hiltComposeVersion")

    // Coil
    // https://coil-kt.github.io/coil/#download
    val coilVersion = "2.6.0"
    implementation("io.coil-kt:coil-compose:2.6.0")

}
