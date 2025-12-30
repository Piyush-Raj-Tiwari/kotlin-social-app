plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    application
}
group = "com.taskscheduler"
version = "0.0.1"

repositories {
    mavenCentral()
}

application {
    mainClass = "post.ApplicationKt"
}

dependencies {

    /* ---------------- Ktor core ---------------- */
    implementation("io.ktor:ktor-server-core-jvm:2.3.7")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.7")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.7")
    implementation("io.ktor:ktor-server-status-pages-jvm:2.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")


    /* ---------------- Logging ---------------- */
    implementation("ch.qos.logback:logback-classic:1.4.14")

    /* ---------------- Testing ---------------- */
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}