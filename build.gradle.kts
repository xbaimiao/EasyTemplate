plugins {
    java
    id("com.github.johnrengelman.shadow") version ("8.1.1")
    kotlin("jvm") version "1.9.20"
}

group = "com.xbaimiao.template"
version = "1.0.0"

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
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.xbaimiao:easy-lib:3.5.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

//    implementation("de.tr7zw:item-nbt-api:2.12.2")
//    implementation("com.zaxxer:HikariCP:4.0.3")
//    implementation("redis.clients:jedis:5.0.1")
//    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly(fileTree("libs"))
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
        dependencies {
            exclude(dependency("org.slf4j:"))
            exclude(dependency("org.jetbrains:annotations:"))
            exclude(dependency("com.google.code.gson:gson:"))
        }

        exclude("LICENSE")
        exclude("META-INF/*.SF")
        archiveClassifier.set("")
        // kotlin
        relocate("kotlin", "${project.group}.shadow.kotlin")
        relocate("kotlinx", "${project.group}.shadow.kotlinx")
        // easylib
        relocate("com.xbaimiao.easylib", "${project.group}.shadow.easylib")
        // librarys
        relocate("redis.clients.jedis", "${project.group}.shadow.redis")
        relocate("com.zaxxer.hikari", "${project.group}.shadow.hikari")
        relocate("de.tr7zw.changeme.nbtapi", "${project.group}.shadow.itemnbtapi")
        minimize()
    }

}
