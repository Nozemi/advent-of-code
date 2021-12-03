import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.Year
import java.time.MonthDay

val currentYear = Year.now().value
val currentDay = MonthDay.now().dayOfMonth

plugins {
    application
    kotlin("jvm") version "1.6.0"
}

group = "io.nozemi.aoc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

application {
    mainClass.set("io.nozemi.aoc.Application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.3.0")
    implementation("com.michael-bull.kotlin-inline-logger:kotlin-inline-logger:1.0.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    testImplementation(kotlin("test"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "16"
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.create<JavaExec>("runThisYear") {
    group = "application"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.Application")
    args = "-y$currentYear -d1-25 -t0".split(" ")
}

tasks.create<JavaExec>("runToday") {
    group = "application"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.Application")
    args = "-y$currentYear -d$currentDay -t0".split(" ")
}

tasks.create<JavaExec>("runWithInput") {
    group = "application"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.Application")
    standardInput = System.`in`
}