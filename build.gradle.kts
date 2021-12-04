import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.MonthDay
import java.time.Year

val currentYear = Year.now().value
val currentDay = MonthDay.now().dayOfMonth

plugins {
    application
    kotlin("jvm") version "1.6.0"
    id("net.researchgate.release") version "2.8.1"
}

group = "io.nozemi.aoc"
java.sourceCompatibility = JavaVersion.VERSION_17

application {
    mainClass.set("io.nozemi.aoc.Application")
}

release {
    failOnUpdateNeeded = false
    revertOnFail = false
    tagTemplate = "v${version}"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.3.0")
    implementation("com.michael-bull.kotlin-inline-logger:kotlin-inline-logger:1.0.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    testImplementation(kotlin("test"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.jar {
    manifest {
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = "io.nozemi.aoc.Application"
    }

    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
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