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
include("fit_changes-be-common")
include("fitChanges-be-mapping")
include("fit_changes-be-utils")
include("fit_changes-be-ktor-product")
include("test_kafka")
include("fit_changes-be-product-service")
include("fit_changes-be-product-logics")
