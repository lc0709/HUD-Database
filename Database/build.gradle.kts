plugins {
    id("java")
}

group = "net.doiche.database"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(project(":HUD"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}