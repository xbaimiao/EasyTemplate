plugins {
    java
    id("com.github.johnrengelman.shadow") version ("8.1.1")
    kotlin("jvm") version "1.9.20"
}

group = "com.xbaimiao.template"
version = "1.0.0"

val pluginMain = "com.xbaimiao.template.EasyTemplate"
val pluginName = "EasyTemplate"
val pluginUpdateInfo = "更新消息"

repositories {
    mavenCentral()
    maven {
        credentials {
            username = project.findProperty("githubUsername").toString()
            password = project.findProperty("githubPassword").toString()
        }
        name = "GithubPackages"
        url = uri("https://maven.pkg.github.com/xbaimiao/EasyLib")
    }
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    implementation("com.xbaimiao:easy-lib:3.6.8")

    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("de.tr7zw:item-nbt-api:2.12.2")

//    compileOnly("com.zaxxer:HikariCP:4.0.3")
//    compileOnly("redis.clients:jedis:5.0.1")
//    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly(fileTree("libs"))
}

class Relocate(
    val pattern: String,
    val replacement: String,
    val cloud: Boolean = false,
)


val relocateList = listOf(
    Relocate("com.xbaimiao.easylib", "${project.group}.shadow.easylib"),

    Relocate("kotlin", "${project.group}.shadow.kotlin", true),
    Relocate("kotlinx", "${project.group}.shadow.kotlinx", true),
    Relocate("redis.clients.jedis", "${project.group}.shadow.redis", true),
    Relocate("com.zaxxer.hikari", "${project.group}.shadow.hikari", true),
    Relocate("de.tr7zw.changeme.nbtapi", "${project.group}.shadow.itemnbtapi", true)
)

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        dependencies {
            exclude(dependency("org.slf4j:"))
            exclude(dependency("org.jetbrains:annotations:"))
            exclude(dependency("com.google.code.gson:gson:"))
            exclude(dependency("org.jetbrains.kotlin:"))
            exclude(dependency("org.jetbrains.kotlinx:"))
        }

        exclude("LICENSE")
        exclude("META-INF/*.SF")
        archiveClassifier.set("")
        relocateList.forEach {
            relocate(it.pattern, it.replacement)
        }
        minimize()
    }
    processResources {
        expand("version" to project.version)

        val relocateAnchor = "relocate: # inject"
        filter { line ->
            var replace = line
            if (line.contains(relocateAnchor)) {
                replace = line.replace(
                    relocateAnchor,
                    "relocate: \r\n" + relocateList.filter { it.cloud }
                        .joinToString("\r\n") { "  - \"${it.pattern}!${it.replacement}\"" })
            }
            if (line.contains("main: # inject")) {
                replace = "main: $pluginMain"
            }
            if (line.contains("name: # inject")) {
                replace = "name: $pluginName"
            }
            if (line.contains("update-info: # inject")) {
                replace = "update-info: \"$pluginUpdateInfo\""
            }
            replace
        }
    }

}
