// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Dependencies.gradle)
        classpath(Dependencies.kotlin)
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

tasks{
    val cleanMe by registering(Delete::class){
        println("started clean")
        delete(buildDir)
        println("clean finished")
    }
}