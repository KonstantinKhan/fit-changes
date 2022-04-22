package ru.fit_changes.backend.app.ktor.product

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.CORS
import ru.fit_changes.backend.product.service.ProductService
import ru.fit_changes.backend.app.ktor.product.configs.AppKtorConfig
import ru.fit_changes.backend.app.ktor.product.routes.registerProductRoutesHttp
import ru.fit_changes.backend.product.logics.ProductCrud
import ru.fit_changes.backend.repo.product.IRepoProduct

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(
    testing: Boolean = false,
    config: AppKtorConfig = AppKtorConfig()
) {
    val productService = ProductService(ProductCrud(config.contextConfig))

    println("repoProductTest is NONE: ${config.contextConfig.repoProductTest == IRepoProduct.NONE}")
    println("repoProductProd is NONE: ${config.contextConfig.repoProductProd == IRepoProduct.NONE}")

    install(CORS) {
        allowHost("localhost:4200")
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)
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