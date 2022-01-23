rootProject.name = "fitChanges"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openapiGenerator: String by settings

        kotlin("jvm") version kotlinVersion
        id("org.openapi.generator") version openapiGenerator
    }
}

include("fitChanges-be-transport-openapi")
include("fitChanges-be-common")
include("fitChanges-be-mapping")
include("fitChanges-be-utils")
