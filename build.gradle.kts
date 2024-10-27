plugins {
    kotlin("jvm") version "1.9.23"
}

group = "br.com.alves.biometric-tool"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(files("libs/opencv-453.jar"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}