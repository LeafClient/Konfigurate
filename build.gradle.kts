plugins {
    java
    kotlin("jvm") version "1.4.0-rc"
    `maven-publish`
}

group = "fr.shyrogan"
version = "1.1.8"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("http://jitpack.io")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("com.github.LeafClient", "Trunk", "1.1.1")
    implementation("com.google.code.gson", "gson", "2.8.0")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}