package com.xbaimiao.easylib

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.net.URI

/**
 * GradlePlugin
 *
 * @author xbaimiao
 * @since 2023/8/29 14:10
 */
class EasylibGradlePlugin : Plugin<Project> {

    override fun apply(thisProject: Project) {
        thisProject.allprojects { project ->
            project.repositories.maven {
                it.credentials { credentials ->
                    credentials.username = thisProject.findProperty("easyLibUsername").toString()
                    credentials.password = thisProject.findProperty("easyLibPassword").toString()
                }
                it.name = "GithubPackages"
                it.url = URI("https://maven.pkg.github.com/xbaimiao/EasyLib")
            }
        }

        val easylibExt = thisProject.extensions.create("easylib", EasyLibExtension::class.java)

        thisProject.afterEvaluate {
            thisProject.dependencies.add("implementation", "com.xbaimiao:easy-lib:${easylibExt.version}")
            thisProject.subprojects {
                it.dependencies.add("compileOnly", "com.xbaimiao:easy-lib:${easylibExt.version}")
            }
        }
    }

}
