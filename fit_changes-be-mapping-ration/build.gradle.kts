plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":fit_changes-be-common"))
    implementation(project(":fit_changes-be-transport-openapi-ration"))
    testImplementation(project(":fit_changes-be-utils"))

    testImplementation(kotlin("test"))
}