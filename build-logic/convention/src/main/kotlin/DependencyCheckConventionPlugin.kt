import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.closureOf
import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension
import org.owasp.dependencycheck.gradle.extension.NvdExtension

class DependencyCheckConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.owasp.dependencycheck")

            extensions.configure<DependencyCheckExtension>("dependencyCheck") {
                // https://nvd.nist.gov/vuln-metrics/cvss
                // Set this to 4F once vulnerabilities are investigated
                failBuildOnCVSS = 11F
                failOnError = false

                suppressionFile = "${project.projectDir}/config/dependency-check/suppressions.xml"

                // Only want Java/Kotlin-specific analyzers
                analyzers.apply {
                    experimentalEnabled = false
                    nugetconfEnabled = false
                    pyPackageEnabled = false
                    pyDistributionEnabled = false
                    rubygemsEnabled = false
                    nodeEnabled = false
                    assemblyEnabled = false
                    msbuildEnabled = false
                    nuspecEnabled = false
                    retirejs.enabled = false
                    nodeAudit.enabled = false
                }

                System.getenv().getOrDefault(NVD_API_KEY, findProperty(NVD_API_KEY))?.also { apiKey ->
                    if ((apiKey as String).isNotBlank()) {
                        nvd(
                            closureOf<NvdExtension> {
                                this.apiKey = apiKey
                            }
                        )
                    }
                }

                // https://github.com/jeremylong/DependencyCheck/issues/6107#issuecomment-1824010802
                nvd.delay = 16000
            }
        }
    }

    companion object {
        private const val NVD_API_KEY = "NVD_API_KEY"
    }
}
