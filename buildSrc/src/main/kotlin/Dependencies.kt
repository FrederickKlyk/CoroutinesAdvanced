object Dependencies{
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val lifecycle_compiler = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    val viewmodel_scope =    "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val lifecycle_testing = "androidx.arch.core:core-testing:${Versions.lifecycle_test}"

    val koin_core = "org.koin:koin-core:${Versions.koin}"
    val koin_android = "org.koin:koin-android:${Versions.koin}"
    val koin_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    val material = "com.google.android.material:material:${Versions.material}"

    val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"

    val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment_version}"
    val fragment_testing = "androidx.fragment:fragment-testing:${Versions.fragment_version}"
}