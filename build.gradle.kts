plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm")
}

group = "ru.itmo.idu"
version = "1.0.3"

repositories {
    mavenCentral()
}

val githubUser: String? = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
val githubPass: String? = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")

dependencies {
    api("org.locationtech.jts:jts-core:1.20.0")

    api("com.google.code.gson:gson:2.13.2")
    implementation("com.github.filosganga:geogson-core:1.4.31")
    implementation("com.github.filosganga:geogson-jts:1.4.31")


    testImplementation(platform("org.junit:junit-bom:6.0.2"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.2")
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Urban-Research-Lab/jts-geojson")
            credentials {
                username = githubUser
                password = githubPass
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}