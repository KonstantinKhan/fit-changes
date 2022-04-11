plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project
    implementation(kotlin("stdlib"))
    implementation(kotlin("test-junit"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation(project(":fit_changes-be-common"))
}