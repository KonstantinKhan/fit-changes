plugins {
    kotlin("jvm")
}

dependencies {

    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(project(":fit_changes-be-common"))
    implementation(project(":fit_changes-be-utils"))
    implementation(project(":fit_changes-cor"))

    implementation(project(":fit_changes-be-cassandra"))
    implementation(project(":fit_changes-be-repo-inmemory"))

    testImplementation(kotlin("test"))
}