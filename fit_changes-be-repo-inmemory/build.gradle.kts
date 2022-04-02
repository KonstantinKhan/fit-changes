plugins {
    kotlin("jvm")
}

dependencies {
    val ehcacheVersion: String by project
    implementation(kotlin("stdlib"))

    // https://mvnrepository.com/artifact/org.ehcache/ehcache
    implementation("org.ehcache:ehcache:$ehcacheVersion")

    implementation(project(":fit_changes-be-common"))

}