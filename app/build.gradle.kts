plugins {
    id("com.android.application")
    jacoco
    id("androidx.navigation.safeargs")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"
    defaultConfig {
        applicationId = "de.adesso_mobile.coroutinesadvanced"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        pickFirst("META-INF/kotlinx-coroutines-core.kotlin_module")
        pickFirst("META-INF/ktor-http.kotlin_module")
        pickFirst("META-INF/ktor-utils.kotlin_module")
        pickFirst("META-INF/ktor-io.kotlin_module")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(Dependencies.fragment)

    // Lifecycle
    implementation(Dependencies.lifecycle_viewModel)
    implementation(Dependencies.lifecycle_liveData)
    implementation(Dependencies.lifecycle_compiler)
    testImplementation(Dependencies.lifecycle_testing)

    // Navigation
    implementation(Dependencies.navigation_fragment)
    implementation(Dependencies.navigation_ui)

    // Logging
    implementation(Dependencies.timber)

    // Koin
    implementation(Dependencies.koin_core)
    implementation(Dependencies.koin_android)
    implementation(Dependencies.koin_viewmodel)

    // Coroutines
    implementation(Dependencies.coroutines_core)
    implementation(Dependencies.coroutines_android)
    testImplementation(Dependencies.coroutines_test)

    // Ktor
    implementation(Dependencies.ktor_client_android)
    implementation(Dependencies.ktor_client_gson)
    implementation(Dependencies.ktor_client_okHttp)
    implementation(Dependencies.ktor_client_logging_jvm)
    implementation(Dependencies.okHttp_logging)
    // Ktor Mock-Engine
    api(Dependencies.ktor_client_mock_engine_jvm)

    // Viewpager2
    implementation(Dependencies.viewpager2)

    // material
    implementation(Dependencies.material)

    //Result<V,E>
    implementation(Dependencies.kittinunf_result)
    implementation(Dependencies.kittinunf_result_coroutines)

    // Test
    testImplementation(Dependencies.mockito_kotlin)
    implementation(Dependencies.fragment_testing)
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
