plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version ("7.1.2")
    kotlin("jvm") version "1.8.0"
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
    implementation("com.xbaimiao:EasyLib:1.6.2")
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
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
//    compileOnly ("com.mojang:authlib:1.5.21")
//    compileOnly ("ink.ptms:nms-all:1.0.0")
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
        relocate("com.cryptomorin.xseries", project.group.toString() + ".shadow.xseries")
        relocate("io.papermc.lib", project.group.toString() + ".shadow.paperlib")
        relocate("com.zaxxer.hikari", project.group.toString() + ".shadow.hikari")
        relocate("de.tr7zw.changeme.nbtapi", project.group.toString() + ".shadow.nbtapi")
        relocate("com.j256.ormlite", project.group.toString() + ".shadow.ormlite")
        relocate("com.xbaimiao.easylib", project.group.toString() + ".easylib")
        relocate("kotlin", project.group.toString() + ".kotlin")
        relocate("de.tr7zw", project.group.toString() + ".shadow.nbt")
        dependencies {
            exclude(dependency("org.slf4j:"))
            exclude(dependency("com.google.code.gson:gson:"))
        }
        exclude("LICENSE")
        exclude("META-INF/*.SF")
    }
    assemble {
        dependsOn(clean)
    }
    processResources {
        include("plugin.yml")
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