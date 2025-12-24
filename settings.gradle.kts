pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version "1.9.22"
        kotlin("plugin.serialization") version "1.9.22"
    }
}

rootProject.name = "task-scheduler-platform"

include("task-service")
include("auth-service")
