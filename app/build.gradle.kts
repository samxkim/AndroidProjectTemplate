plugins {
    alias(libs.plugins.junit5Android)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt") // Add this line to apply the Kotlin Annotation Processing (kapt) plugin
    alias(libs.plugins.secrets)
    alias(libs.plugins.kotlinXKover)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "dev.mikuwu.androidprojecttemplate"
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        applicationId = "dev.mikuwu.androidprojecttemplate"
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            manifestPlaceholders["appName"] = "Android Project Template"
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            manifestPlaceholders["appName"] = "Android Project Template Debug"
        }
    }

    compileOptions {
        // https://stackoverflow.com/questions/66556819/android-corelibrarydesugaring-which-java-11-apis-can-i-expect-to-work
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
        buildConfig = true
        aidl = true
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    lint {
        abortOnError = false
    }

    packagingOptions {
        resources {
            excludes += listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/LICENSE.md",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/atomicfu.kotlin_module",
                "META-INF/MANIFEST.MF",
                "META-INF/io.netty.versions.properties",
                "META-INF/INDEX.LIST",
                "META-INF/LICENSE-notice.md",
                "META-INF/NOTICE.md",
                "META-INF/LICENSE.md",
                "META-INF/NOTICE.markdown"
            )
        }
    }

}

dependencies {
    // Kover (Merge multiple modules into one report)
    kover(project(":app"))

    // AndroidX
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.fragment.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.work.runtime.ktx)

    // Ktor client
    implementation(libs.bundles.ktorClientBundle)
    testImplementation(libs.ktor.client.mock)

    // Json
    implementation(libs.bundles.jacksonBundle)

    // Test
    testImplementation(libs.bundles.mockkBundle)
    androidTestImplementation(libs.bundles.mockkBundle)
    testImplementation(libs.bundles.junitBundle)
    androidTestImplementation(libs.bundles.junitBundle)

    // Optional -- Robolectric environment
    testImplementation(libs.androidx.core)
    testImplementation(libs.robolectric)

    testImplementation(libs.bundles.mannodermausBundle)
    androidTestImplementation(libs.junit.jupiter.api)

    //For runBlockingTest, CoroutineDispatcher etc.
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)

    // Koin for Kotlin Multiplatform
    implementation(libs.koin.core)
    // Koin Test for Kotlin Multiplatform
    // api "io.insert-koin:koin-test:$koin_version"

    // Koin for JUnit 5
    testImplementation(libs.koin.test.junit5)
    androidTestImplementation(libs.koin.test.junit5)
    // Koin Extended & experimental features (JVM)
    implementation(libs.koin.core.ext)

    // Koin main features for Android (Scope,ViewModel ...)
    implementation(libs.koin.android)

    // Koin Android - experimental builder extensions
    implementation(libs.koin.android.ext)
    // Koin for Jetpack WorkManager
    implementation(libs.koin.androidx.workmanager)
    // Koin for Jetpack Compose
    implementation(libs.koin.androidx.compose)

    // SLF4J Logger
    implementation(libs.koin.logger.slf4j)
    implementation(libs.kotlin.logging.jvm)
    implementation(libs.slf4j.api)
    implementation(libs.logback.android)

    implementation(libs.guava)

    // Android Room Database
    implementation(libs.androidx.room.common)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.room.compiler)

    // Mapstruct
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)

    // Result (monad)
    implementation(libs.kotlin.result)
    implementation(libs.kotlin.result.coroutines)

    // Retry
    implementation(libs.kotlin.retry)

    // Bouncy Castle
    implementation(libs.bcprov.jdk18on)

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    implementation("androidx.compose.runtime:runtime-livedata")
    androidTestImplementation(composeBom)
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // For Android Instrumented Testing
    // https://eventcollapse.com/posts/android-syntax-desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // Capturable Compose
    implementation(libs.composeBitmap)

    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation(libs.leakcanary.android)

    // Coil
    implementation(libs.coilCompose)

    // Android App Startup
    implementation(libs.appStartup)

}

secrets {
    ignoreList.add("sdk.dir")
}
