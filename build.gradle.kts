import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.7.10" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
group = "net.doiche.core"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
}
tasks.test {
    useJUnitPlatform()
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
allprojects {

    apply {
        plugin("kotlin")
    }
    tasks.withType(JavaCompile::class.java){
        options.encoding = "UTF-8"
    }
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
    dependencies {
        implementation("com.zaxxer:HikariCP:4.0.3")
        implementation("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    }
}