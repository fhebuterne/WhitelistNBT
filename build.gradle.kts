import java.net.URI

plugins {
    kotlin("jvm") version Versions.kotlin
    id("com.github.johnrengelman.shadow") version Versions.shadowJar
}

group = "fr.fabienhebuterne"
version = System.getProperty("tagVersion") ?: "SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = URI("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        content {
            includeGroup("org.bukkit")
            includeGroup("org.spigotmc")
        }
    }
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.fasterxml.jackson.core:jackson-core:${Versions.jackson}")
    compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    //compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformats-text:${Versions.jackson}")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Versions.jackson}")
    compileOnly(kotlin("stdlib"))
}

tasks.processResources {
    filesMatching("**/**.yml") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        expand(project.properties)
    }
}

val buildVersion: String? by project
val pluginName = rootProject.name

tasks.shadowJar {
    if (buildVersion == null) {
        archiveFileName.set("$pluginName-${archiveVersion.getOrElse("unknown")}.jar")
    } else {
        // For ci/cd
        archiveFileName.set("$pluginName.jar")
    }

    destinationDirectory.set(file(System.getProperty("outputDir") ?: "$rootDir/build/"))
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = "17"
    }
}