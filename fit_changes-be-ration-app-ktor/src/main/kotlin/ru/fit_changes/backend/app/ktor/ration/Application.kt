package ru.fit_changes.backend.app.ktor.ration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import ru.fit_changes.backend.app.ktor.ration.routes.rationRoutes
import ru.fit_changes.backend.ration.logics.RationCrud
import ru.fit_changes.backend.ration.service.RationService

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(
    config: AppKtorConfig = AppKtorConfig()
) {
    val rationService = RationService(crud = RationCrud())

    rationRoutes(rationService)

    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            enable(SerializationFeature.INDENT_OUTPUT)
            writerWithDefaultPrettyPrinter()
        }
    }
}

fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            enable(SerializationFeature.INDENT_OUTPUT)
            writerWithDefaultPrettyPrinter()
        }
    }
}