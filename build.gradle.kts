plugins {
    kotlin("jvm") version "1.5.10"
}

group = "io.mhssn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.jakewharton.picnic:picnic:0.6.0")
    testImplementation(kotlin("test"))
}