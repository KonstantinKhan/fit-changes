plugins {
    kotlin("jvm") apply false
    id("org.openapi.generator") apply false
    id("com.bmuschko.docker-java-application") apply false
}

group = "ru.fit_changes"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        mavenCentral()
    }
}