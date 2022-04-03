package ru.fit_changes.backend.app.ktor.product

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import ru.fit_chages.backend.product.service.ProductService
import ru.fit_changes.backend.app.ktor.product.configs.AppKtorConfig
import ru.fit_changes.backend.app.ktor.product.routes.registerProductRoutesHttp
import ru.fit_changes.backend.product.logics.ProductCrud

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(
    testing: Boolean = false,
    config: AppKtorConfig = AppKtorConfig()
) {
    val productService = ProductService(ProductCrud(config.contextConfig))

    install(CORS) {
        host("localhost:4200")
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.ContentType)
    }

    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            enable(SerializationFeature.INDENT_OUTPUT)
            writerWithDefaultPrettyPrinter()
        }
    }

    routing {
        get("/") {
            println(call.response.headers.allValues())
            call.respondText("Hello, World")
        }
    }

    /* registerProductRoutes()
     routing {
         kafka(service = productService)
         get("/") {
             call.respondText("Hello, World")
         }
     }*/

    registerProductRoutesHttp(productService)
}