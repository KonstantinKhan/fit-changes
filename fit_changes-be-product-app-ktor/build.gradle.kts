plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass)
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    val ktorVersion: String by project
    val kotlinVersion: String by project
    val logbackVersion: String by project
    val kafkaVersion: String by project
    val coroutinesVersion: String by project

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")

    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")

    implementation(project(":fit_changes-be-transport-openapi-product"))
    implementation(project(":fit_changes-be-common"))
    implementation(project(":fit_changes-be-product-service"))
    implementation(project(":fit_changes-be-product-logics"))

    implementation(project(":fit_changes-be-cassandra"))
    implementation(project(":fit_changes-be-repo-inmemory"))

    implementation(project(":fit_changes-be-utils"))
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
}
