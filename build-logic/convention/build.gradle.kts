import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.gradleDoctor) apply false
    alias(libs.plugins.kotlinXKover) apply false
    alias(libs.plugins.kotlinDetekt) apply false
    alias(libs.plugins.owaspDependencyCheck) apply false
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

group = "com.payment"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)

    compileOnly(libs.gradleDoctor.gradlePlugin)
    compileOnly(libs.kotlinXKover.gradlePlugin)
    compileOnly(libs.kotlinDetekt.gradlePlugin)
    compileOnly(libs.owaspDependencyCheck.gradlePlugin)

    compileOnly(libs.detekt.cli)
    compileOnly(libs.detekt.formatting)
}

gradlePlugin {
    plugins {
        register("gradle-doctor-convention") {
            id = "conventionPlugin.gradleDoctor"
            implementationClass = "GradleDoctorConventionPlugin"
        }

        register("kover-convention") {
            id = "conventionPlugin.kover"
            implementationClass = "KoverConventionPlugin"
        }

        register("detekt-convention") {
            id = "conventionPlugin.detekt"
            implementationClass = "DetektConventionPlugin"
        }

        register("dependencyCheck-convention") {
            id = "conventionPlugin.dependencyCheck"
            implementationClass = "DependencyCheckConventionPlugin"
        }

        register("androidLibrary-convention") {
            id = "conventionPlugin.androidLibrary"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidApplication-convention") {
            id = "conventionPlugin.androidApplication"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
