plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

openApiGenerate {
    val openapiGroup = "${rootProject.group}.openapi"
    generatorName.set("kotlin")
    packageName.set(openapiGroup)
    modelPackage.set("$openapiGroup.models")
    inputSpec.set("$rootDir/specs/spec-fitChanges-api-product.yaml")

    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
        )
    )
}

sourceSets {
    main {
        java.srcDir("$buildDir/generate-resources/main/src/main/kotlin")
    }
}

dependencies {
    val jacksonVersion: String by project
    val kotlinVersion: String by project

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))

    api("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    api("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")

    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    testImplementation(project(":fit_changes-be-utils"))
}