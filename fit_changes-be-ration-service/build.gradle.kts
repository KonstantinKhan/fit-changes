plugins {
    kotlin("jvm")
}

dependencies {

    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(project(":fit_changes-be-transport-openapi-ration"))
    implementation(project(":fit_changes-be-common"))
    implementation(project(":fit_changes-be-mapping-ration"))
    implementation(project(":fit_changes-be-ration-logics"))

}