plugins {
    id("java")
    kotlin("jvm")
}

group = "ru.itmo.idu"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    api("org.locationtech.jts:jts-core:1.19.0")

    api("com.google.code.gson:gson:2.8.9")
    implementation("com.github.filosganga:geogson-core:1.4.31")
    implementation("com.github.filosganga:geogson-jts:1.4.31")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}