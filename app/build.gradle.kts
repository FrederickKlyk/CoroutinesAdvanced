plugins {
    id("com.android.application")
    jacoco
    id("androidx.navigation.safeargs")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-android")
}
apply {
    from("../buildSrc/jacoco.gradle")
}

jacoco {
    toolVersion = "0.8.5"
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"
    defaultConfig {
        applicationId = "de.klyk.coroutinesadvanced"
        minSdk = 23
        targetSdk = 31
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources.pickFirsts.add("META-INF/kotlinx-coroutines-core.kotlin_module")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":dummy"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(Dependencies.fragment)

    // Lifecycle
    implementation(Dependencies.lifecycle_viewModel)
    implementation(Dependencies.lifecycle_liveData)
    implementation(Dependencies.lifecycle_compiler)
    testImplementation(Dependencies.lifecycle_testing)

    // Paging
    implementation(Dependencies.paging)

    // Room
    implementation(Dependencies.room_runtime)
    implementation(Dependencies.room_ktx)
    kapt(Dependencies.room_compiler)

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
    implementation(Dependencies.ktor_client_mock_engine_jvm)

    // Viewpager2
    implementation(Dependencies.viewpager2)

    // material
    implementation(Dependencies.material)

    // Coil
    implementation(Dependencies.coil)

    // Palette
    implementation("androidx.palette:palette-ktx:1.0.0")

    //Result<V,E>
    implementation(Dependencies.kittinunf_result)
    implementation(Dependencies.kittinunf_result_coroutines)

    // Test
    testImplementation(Dependencies.mockito_kotlin)
    testImplementation(Dependencies.mockito_kotlin_inline)
    implementation(Dependencies.fragment_testing)
    testImplementation("org.amshove.kluent:kluent-android:1.61")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
