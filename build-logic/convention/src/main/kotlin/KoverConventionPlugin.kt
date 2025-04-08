import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("org.jetbrains.kotlinx.kover")

            extensions.configure<KoverProjectExtension> {
                reports {
                    filters {
                        excludes {
                            androidGeneratedClasses()
                        }
                    }

                    variant("release") {
                        verify {
                            rule {
                                minBound(50)
                            }
                        }

                        filters {
                            excludes {
                                androidGeneratedClasses()
                                classes(
                                    "*.DebugUtil"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
