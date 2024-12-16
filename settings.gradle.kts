pluginManagement {
    repositories {
        maven("https://maven.xbaimiao.com/repository/maven-public/")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    //kotlin 版本
    val ktVersion: String by settings
    //shadowJar 版本
    val shadowJarVersion: String by settings
    val easylibPluginVersion: String by settings
    plugins {
        kotlin("jvm") version ktVersion
        id("io.github.goooler.shadow") version shadowJarVersion
        id("com.xbaimiao.easylib") version easylibPluginVersion
    }
}
rootProject.name = "EasyTemplate"
