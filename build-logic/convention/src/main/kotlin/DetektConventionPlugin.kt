@file:Suppress("ktlint:standard:no-wildcard-imports")

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
import org.gradle.kotlin.dsl.*
import java.io.File

class DetektConventionPlugin : Plugin<Project> {
    private val detektAllTask = "detektAll"

    override fun apply(target: Project) {
        with(target) {
            val reportMerge by tasks.registering(ReportMergeTask::class) {
                // or "reports/detekt/merge.xml"
                output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
            }

            val baselineFile = File("$projectDir/detekt-baseline.xml")

            pluginManager.apply("io.gitlab.arturbosch.detekt")

            tasks.withType<Detekt> {
                parallel = true
                config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
                buildUponDefaultConfig = false
                autoCorrect = true

                reports.xml.required.set(true)
                reports.sarif.required.set(true)

                // Set baseline file for this module.
                if (baselineFile.exists()) {
                    baseline.set(baselineFile)
                }
            }

            tasks.withType<Detekt>().configureEach {
                finalizedBy(reportMerge)
            }

            reportMerge {
                // or .xmlReportFile
                input.from(tasks.withType<Detekt>().map { it.sarifReportFile })
            }

            // To generate a singular file with all of the detekt reports
            tasks.register<Detekt>(detektAllTask) {
                parallel = true
                setSource(files(projectDir))
                include("**/*.kt")
                include("**/*.kts")
                exclude("**/resources/**")
                exclude("**/build/**")
                config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
                buildUponDefaultConfig = false
                // Failures should be checked prior to merging code..
                ignoreFailures = false

                // Set baseline file for this module.
                if (baselineFile.exists()) {
                    baseline.set(baselineFile)
                }
            }

            dependencies {
                add("detekt", "io.gitlab.arturbosch.detekt:detekt-cli:1.23.3")
                add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:1.23.3")
            }
        }
    }
}
