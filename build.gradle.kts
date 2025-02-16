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
//    library("com.zaxxer:HikariCP:4.0.3", true)
//    library("com.j256.ormlite:ormlite-core:6.1", true)
//    library("com.j256.ormlite:ormlite-jdbc:6.1", true)
//    relocate("com.zaxxer.hikari", "${project.group}.shadow.hikari", true)
//    relocate("com.j256.ormlite", "${project.group}.shadow.ormlite", true)
    relocate("com.xbaimiao.easylib", "${project.group}.easylib", false)
    relocate("kotlin", "${project.group}.shadow.kotlin", true)
    relocate("kotlinx", "${project.group}.shadow.kotlinx", true)
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
//    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
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
