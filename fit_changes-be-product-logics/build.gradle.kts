plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":fit_changes-be-common"))

    testImplementation(kotlin("test"))
}