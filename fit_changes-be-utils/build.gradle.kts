plugins {
    kotlin("jvm")
}

group = "ru.fitChanges"
version = "0.0.1"

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":fit_changes-be-transport-openapi"))
    implementation(project(":fit_changes-be-common"))
    implementation(project(":fit_changes-be-mapping"))
}