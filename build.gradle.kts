import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.MonthDay
import java.time.Year

val currentYear = Year.now().value
val currentDay = MonthDay.now().dayOfMonth

plugins {
    application
    kotlin("jvm") version "2.1.0"
    id("net.researchgate.release") version "2.8.1"
}

group = "io.nozemi.aoc"
java.sourceCompatibility = JavaVersion.VERSION_21

application {
    mainClass.set("io.nozemi.aoc.Application")
}

release {
    tagTemplate = "v${version}"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.classgraph:classgraph:4.8.137")
    implementation("com.github.ajalt.clikt:clikt:3.3.0")
    implementation("com.michael-bull.kotlin-inline-logger:kotlin-inline-logger:1.0.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.15.0")
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
        jvmTarget = "21"
    }
}

tasks.jar {
    manifest {
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = "io.nozemi.aoc.ApplicationKt"
    }

    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.test {
    useJUnitPlatform()
}

tasks.create<JavaExec>("injectNewReadme") {
    group = "tools"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.tools.ToolSelectorScreen")
}

tasks.create<JavaExec>("runAll") {
    group = "application"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.ApplicationKt")
    args = "-y2000-3000 -d1-25 -t0".split(" ")
}

tasks.create<JavaExec>("runThisYear") {
    group = "application"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.ApplicationKt")
    args = "-y$currentYear -d1-25 -t0".split(" ")
}

tasks.create<JavaExec>("runYesterday") {
    group = "application"

    //dependsOn(":injectNewReadme")

    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.ApplicationKt")
    args = "-y$currentYear -d${currentDay - 1} -t0".split(" ")
}

tasks.create<JavaExec>("runToday") {
    group = "application"

    //dependsOn(":injectNewReadme")

    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.ApplicationKt")
    args = "-y$currentYear -d$currentDay -t0".split(" ")
}

tasks.create<JavaExec>("runWithInput") {
    group = "application"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.ApplicationKt")
    standardInput = System.`in`
}