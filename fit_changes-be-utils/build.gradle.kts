plugins {
    kotlin("jvm")
}

group = "ru.fitChanges"
version = "0.0.1"

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":fit_changes-be-transport-openapi-product"))
    implementation(project(":fit_changes-be-transport-openapi-ration"))
    implementation(project(":fit_changes-be-common"))
}