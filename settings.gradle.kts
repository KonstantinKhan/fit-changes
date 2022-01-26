rootProject.name = "fit_changes"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openapiGenerator: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion
        id("org.openapi.generator") version openapiGenerator

        id("com.bmuschko.docker-java-application") version bmuschkoVersion
    }
}

include("fitChanges-be-transport-openapi")
include("fitChanges-be-common")
include("fitChanges-be-mapping")
include("fitChanges-be-utils")
include("fit_changes-be-ktor-product")
include("test_kafka")