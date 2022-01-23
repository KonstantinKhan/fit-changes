plugins {
    kotlin("jvm")
}

group = "ru.fitChanges"
version = "0.0.1"

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":fitChanges-be-transport-openapi"))
    implementation(project(":fitChanges-be-common"))
}