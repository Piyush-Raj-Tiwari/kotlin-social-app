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
    mainClass = "auth.ApplicationKt"
}

dependencies {

    /* ---------------- Ktor core ---------------- */
    implementation("io.ktor:ktor-server-core-jvm:2.3.7")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.7")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.7")
    implementation("io.ktor:ktor-server-status-pages-jvm:2.3.7")

    /* ---------------- Logging ---------------- */
    implementation("ch.qos.logback:logback-classic:1.4.14")

    /* ---------------- Auth-specific ---------------- */

    // JWT (access tokens)
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Password hashing (bcrypt)
    implementation("org.mindrot:jbcrypt:0.4")

    /* ---------------- Testing ---------------- */
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
