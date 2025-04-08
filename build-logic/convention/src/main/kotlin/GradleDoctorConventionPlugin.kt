import com.osacky.doctor.DoctorExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleDoctorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.osacky.doctor")

            allprojects {
                afterEvaluate {
                    extensions.findByType(DoctorExtension::class.java)
                        ?.allowBuildingAllAndroidAppsSimultaneously?.set(true)
                }
            }

            extensions.configure<DoctorExtension>("doctor") {
                warnWhenNotUsingParallelGC.set(false)
            }
        }
    }
}
