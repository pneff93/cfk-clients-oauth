import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    application
}

application{
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
    implementation("org.apache.logging.log4j:log4j-core:2.12.4")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.12.4")
    implementation("io.ktor:ktor-server-netty:1.2.2")
    implementation("org.apache.kafka:kafka-clients:3.7.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.0")
}

val fatJar = task("fatJar", type = Jar::class) {

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    baseName = "fatApp"
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}