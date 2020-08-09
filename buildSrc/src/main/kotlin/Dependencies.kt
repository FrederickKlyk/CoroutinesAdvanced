object Dependencies{
    // System libs
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Android Lifecycle (architecture components)
    const val lifecycle_viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycle_testing = "androidx.arch.core:core-testing:${Versions.lifecycle_test}"

    // Koin DI
    const val koin_core = "org.koin:koin-core:${Versions.koin}"
    const val koin_android = "org.koin:koin-android:${Versions.koin}"
    const val koin_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    // Coroutines
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    // Viewpager + Material
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val material = "com.google.android.material:material:${Versions.material}"

    // JUnit
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    const val mockito_kotlin_inline = "org.mockito:mockito-inline:${Versions.mockito_kotlin_inline}"

    //Fragment
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment_version}"
    const val fragment_testing = "androidx.fragment:fragment-testing:${Versions.fragment_version}"

    // Navigation
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
    const val navigation_safe_args =  "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav_version}"

    // Ktor
    const val ktor_client_android = "io.ktor:ktor-client-android:${Versions.ktor_client_android}"
    const val ktor_client_gson = "io.ktor:ktor-client-gson:${Versions.ktor_client_json}"
    const val ktor_client_okHttp = "io.ktor:ktor-client-okhttp:${Versions.ktor_client_android}"
    const val ktor_client_logging_jvm = "io.ktor:ktor-client-logging-jvm:${Versions.ktor_client_android}"
    const val ktor_client_mock_engine_jvm = "io.ktor:ktor-client-mock-jvm:${Versions.ktor_client_android}"

    // OkHttp Logging
    const val okHttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp_logging}"

    // Paging
    const val paging = "androidx.paging:paging-runtime:${Versions.paging_version}"

    // Room
    const val room_runtime =  "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    // Result<V,E>
    const val kittinunf_result = "com.github.kittinunf.result:result:${Versions.kittinunf_result}"
    const val kittinunf_result_coroutines = "com.github.kittinunf.result:result-coroutines:${Versions.kittinunf_result}"
}