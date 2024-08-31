// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlin).apply(false)
    alias(libs.plugins.kotlin.parcelize).apply(false)
    alias(libs.plugins.kotlin.kapt).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.google.services).apply(false)
}
/*buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:7.0.3"
        classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31'
        classpath "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.0"
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath 'com.google.gms:google-services:4.3.14'
    }
}*/
task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
/*task clean(type: Delete) {
    delete rootProject.buildDir
}*/