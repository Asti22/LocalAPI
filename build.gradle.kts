// Project-level build.gradle.kts
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    // Ganti baris compose compiler yang merah pakai ini:
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}