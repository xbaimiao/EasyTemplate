import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
    java
    id("com.github.johnrengelman.shadow") version ("7.1.2")
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.kotlin.plugin.lombok") version "1.8.0"
}

group = "com.xbaimiao.template"
version = "1.0.0"

repositories {
    mavenLocal()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.xbaimiao.com/nexus/content/repositories/releases/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://libraries.minecraft.net/")
    maven {
        url = uri("http://ptms.ink:8081/repository/releases/")
        isAllowInsecureProtocol = true
    }
    mavenCentral()
}

dependencies {
    implementation("com.xbaimiao:EasyLib:1.7.3")
    implementation(kotlin("stdlib-jdk8"))
//    implementation ("net.kyori:adventure-api:4.9.3")
//    implementation ("net.kyori:adventure-platform-bukkit:4.2.0")
//    implementation ("net.kyori:adventure-text-minimessage:4.12.0")
//    implementation ("com.github.cryptomorin:XSeries:9.1.0")
//    implementation ("de.tr7zw:item-nbt-api:2.11.1")
//    implementation ("com.j256.ormlite:ormlite-core:6.1")
//    implementation ("com.j256.ormlite:ormlite-jdbc:6.1")
//    implementation ("com.zaxxer:HikariCP:4.0.3")
//    implementation ("io.papermc:paperlib:1.0.7")
    compileOnly(fileTree("libs"))
    compileOnly("ink.ptms.core:v11903:11903:universal")
//    compileOnly ("com.mojang:authlib:1.5.21")
//    compileOnly ("ink.ptms:nms-all:1.0.0")
}

fun releaseTime() = LocalDate.now().format(DateTimeFormatter.ofPattern("y.M.d"))

// 混淆
tasks.register("confuse") {
    dependsOn("build")
    doLast {
        val jarName = "${project.name}-${releaseTime()}-${project.version}-all.jar"
        val jarFile = File("build/libs/$jarName")
        val allatoriCrackFile = File("allatori/allatori_crack.jar")
        if (!allatoriCrackFile.exists()) {
            throw RuntimeException("allatori_crack.jar not found")
        }
        if (!jarFile.exists()) {
            throw RuntimeException("$jarName not found")
        }
        val bakConfig = File("allatori/config-bak.xml")
        val config = File("allatori/config.xml")
        if (!config.exists()) {
            config.createNewFile()
        }
        config.writeText(
            bakConfig.readText().replace("{input}", "../" + jarFile.path)
                .replace("{out}", "../" + jarFile.path.replace(".jar", "-confuse.jar"))
                .replace("{main}", "${project.group}.${project.name}")
        )
        // 执行控制台命令
        exec {
            // 指定命令
            commandLine("java", "-jar", "allatori/allatori_crack.jar", "allatori/config.xml")
        }
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    shadowJar {
        val relocateFile = File("relocate.properties")
        relocateFile.readText().split("\n").forEach {
            val args = it.split("=")
            relocate(args[0], "${project.group}.shadow.${args[1]}")
        }
        dependencies {
            exclude(dependency("org.slf4j:"))
            exclude(dependency("com.google.code.gson:gson:"))
        }
        exclude("LICENSE")
        exclude("META-INF/*.SF")
        minimize()
        archiveBaseName.set("${project.name}-${releaseTime()}")
    }
    assemble {
        dependsOn(clean)
    }
    processResources {
        val props = ArrayList<Pair<String, Any>>()
        props.add("version" to version)
        props.add("main" to "${project.group}.${project.name}")
        props.add("name" to project.name)
        expand(*props.toTypedArray())
        filteringCharset = "UTF-8"
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    artifacts {
        archives(shadowJar)
        archives(kotlinSourcesJar)
    }
}
