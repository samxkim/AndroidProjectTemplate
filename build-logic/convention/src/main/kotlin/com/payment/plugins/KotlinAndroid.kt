package com.payment.plugins

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        configureKotlin()

        lint {
            abortOnError = false
        }

        packaging {
            resources {
                excludes.add("META-INF/DEPENDENCIES")
                excludes.add("META-INF/LICENSE")
                excludes.add("META-INF/LICENSE.txt")
                excludes.add("META-INF/LICENSE.md")
                excludes.add("META-INF/license.txt")
                excludes.add("META-INF/NOTICE")
                excludes.add("META-INF/NOTICE.txt")
                excludes.add("META-INF/notice.txt")
                excludes.add("META-INF/ASL2.0")
                excludes.add("META-INF/atomicfu.kotlin_module")
                excludes.add("META-INF/MANIFEST.MF")
                excludes.add("META-INF/io.netty.versions.properties")
                excludes.add("META-INF/INDEX.LIST")
                excludes.add("META-INF/LICENSE-notice.md")
                excludes.add("META-INF/NOTICE.md")
                excludes.add("META-INF/LICENSE.md")
                excludes.add("META-INF/NOTICE.markdown")
            }
        }
    }
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }
}
