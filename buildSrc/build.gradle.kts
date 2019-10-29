plugins {
    // note the backtick syntax (since `kotlin-dsl` is
    // an extension property on the plugin's scope object)
    `kotlin-dsl`
}

repositories {
    // The org.jetbrains.kotlin.jvm plugin requires a repository
    // where to download the Kotlin compiler dependencies from.
    jcenter()
}