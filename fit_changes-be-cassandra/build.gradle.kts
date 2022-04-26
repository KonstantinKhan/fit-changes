import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    val coroutinesVersion: String by project
    val cassandraDriverVersion: String by project
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")

    implementation("com.datastax.oss:java-driver-core:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-query-builder:$cassandraDriverVersion")
    kapt("com.datastax.oss:java-driver-mapper-processor:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-mapper-runtime:$cassandraDriverVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion" )

    implementation(project(":fit_changes-be-repo-test"))

    implementation(project(":fit_changes-be-common"))
    implementation(project(":fit_changes-be-utils"))

    // https://mvnrepository.com/artifact/org.testcontainers/cassandra
    testImplementation("org.testcontainers:cassandra:$testContainersVersion")

}