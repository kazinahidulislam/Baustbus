// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    // ভার্সন ডিক্লেয়ারেশন
    val agpVersion by extra("8.2.2")       // Android Gradle Plugin (AGP) latest stable
    val kotlinVersion by extra("1.9.22")   // Kotlin ভার্সন
    val googleServicesVersion by extra("4.4.0")  // Google Services plugin

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Android Gradle Plugin
        classpath("com.android.tools.build:gradle:${project.extra["agpVersion"]}")

        // Kotlin Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlinVersion"]}")

        // Firebase & Google Services (অবশ্যই যোগ করুন)
        classpath("com.google.gms:google-services:${project.extra["googleServicesVersion"]}")
    }
}

// settings.gradle.kts-এ repositories যোগ করার জন্য এখানকার allprojects ব্লক খালি রাখুন
allprojects {
    // ⚠️ Deprecated: repositories এখন settings.gradle.kts-এ যোগ করুন
}

// ডিপ্রিকেটেড buildDir এর পরিবর্তে নতুন মেথড
tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}