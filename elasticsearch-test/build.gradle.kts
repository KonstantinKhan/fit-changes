plugins {
    kotlin("jvm")
}

dependencies {
    val jacksonVersion: String by project

    implementation(kotlin("stdlib"))

    implementation("co.elastic.clients:elasticsearch-java:8.1.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
}