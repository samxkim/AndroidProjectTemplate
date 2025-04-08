// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.kotlinDetekt) apply false
    alias(libs.plugins.kotlinXKover) apply false
    alias(libs.plugins.gradleDoctor) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.owaspDependencyCheck) apply false

    alias(libs.plugins.conventionPlugin.androidLibrary) apply false
    alias(libs.plugins.conventionPlugin.androidApplication) apply false
    alias(libs.plugins.conventionPlugin.gradleDoctor) apply true
    alias(libs.plugins.conventionPlugin.kotlinXKover) apply false
    alias(libs.plugins.conventionPlugin.kotlinDetekt) apply false
    alias(libs.plugins.conventionPlugin.dependencyCheck) apply false
}

subprojects {
    apply<DetektConventionPlugin>()
    apply<KoverConventionPlugin>()
}
