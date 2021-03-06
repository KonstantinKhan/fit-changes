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

include("fit_changes-be-transport-openapi-product")
include("fit_changes-be-common")
include("fit_changes-be-mapping-product")
include("fit_changes-be-mapping-ration")
include("fit_changes-be-utils")
include("fit_changes-be-product-app-ktor")
include("fit_changes-be-ration-app-ktor")
include("test_kafka")
include("fit_changes-be-product-service")
include("fit_changes-be-ration-service")
include("fit_changes-be-product-logics")
include("fit_changes-be-ration-logics")
include("fit_changes-be-cassandra")
include("elasticsearch-test")
include("fit_changes-be-repo-inmemory")
include("fit_changes-be-repo-test")
include("fit_changes-be-transport-openapi-ration")
include("fit_changes-cor")

include("fit_changes-be-ration-repo-inmemory")
include("fit_changes-be-ration-repo-test")
