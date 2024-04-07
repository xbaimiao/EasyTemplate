plugins {
    id("java")
    id("java-gradle-plugin")
    kotlin("jvm") version "1.9.20"
}

group = "com.xbaimiao.template"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin-api"))
}

gradlePlugin {
    plugins {
        create("easylib-gradle-plugin") {
            id = "com.xbaimiao.easylib"
            implementationClass = "com.xbaimiao.easylib.EasylibGradlePlugin"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
