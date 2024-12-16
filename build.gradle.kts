val ktVersion: String by project
val easylibVersion: String by project

plugins {
    java
    id("io.github.goooler.shadow")
    id("com.xbaimiao.easylib")
    kotlin("jvm")
}

group = "com.xbaimiao.template"
version = "1.0.0"

easylib {
    env {
        mainClassName = "com.xbaimiao.template.EasyTemplate"
        pluginName = "EasyTemplate"
        kotlinVersion = ktVersion
    }
    version = easylibVersion
    relocate("com.xbaimiao.easylib", "${project.group}.easylib", false)
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    compileOnly(kotlin("stdlib-jdk8"))
//    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly(fileTree("libs"))
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    processResources {
        outputs.upToDateWhen { false }
    }
    shadowJar {
        dependencies {
            easylib.library.forEach {
                if (it.cloud) {
                    exclude(dependency(it.id))
                }
            }
            exclude(dependency("org.slf4j:"))
            exclude(dependency("org.jetbrains:annotations:"))
            exclude(dependency("com.google.code.gson:gson:"))
            exclude(dependency("org.jetbrains.kotlin:"))
            exclude(dependency("org.jetbrains.kotlinx:"))
        }
        archiveClassifier.set("")
        easylib.relocate.forEach {
            relocate(it.pattern, it.replacement)
        }
        minimize()
    }

}
