package ru.fit_changes.backend.app.ktor.product

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.*
import ru.fit_changes.backend.product.service.ProductService
import ru.fit_changes.backend.app.ktor.product.configs.AppKtorConfig
import ru.fit_changes.backend.app.ktor.product.configs.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.fit_changes.backend.app.ktor.product.routes.registerProductRoutesHttp
import ru.fit_changes.backend.product.logics.ProductCrud
import ru.fit_changes.backend.repo.product.IRepoProduct

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(
    testing: Boolean = false,
    config: AppKtorConfig = AppKtorConfig(environment)
) {
    val productService = ProductService(ProductCrud(config.contextConfig))
    install(Authentication) {
        jwt("auth-jwt") {
            realm = config.auth.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.auth.secret))
                    .withAudience(config.auth.audience)
                    .withIssuer(config.auth.issuer)
                    .build()
            )
            validate { credential ->
                when {
                    credential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        println("Groups claim must not be empty in JWT token")
                        null
                    }
                    else -> JWTPrincipal(credential.payload)
                }
            }
        }
    }

    println("repoProductTest is NONE: ${config.contextConfig.repoProductTest == IRepoProduct.NONE}")
    println("repoProductProd is NONE: ${config.contextConfig.repoProductProd == IRepoProduct.NONE}")

    install(CORS) {
        allowHost("localhost:4200")
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }

    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            enable(SerializationFeature.INDENT_OUTPUT)
            writerWithDefaultPrettyPrinter()
        }
    }

//    routing {
//
//        post("/login") {
//            val token = JWT.create()
//                .withAudience(config.auth.audience)
//                .withIssuer(config.auth.issuer)
//                .withExpiresAt(Date(System.currentTimeMillis() + 6000))
//                .sign(Algorithm.HMAC256(config.auth.secret))
//            call.respond(hashMapOf("token" to token))
//        }
//
//        authenticate("auth-jwt") {
//            get("/") {
//                call.respondText("Hello, World")
//            }
//        }
//    }

    /* registerProductRoutes()
     routing {
         kafka(service = productService)
         get("/") {
             call.respondText("Hello, World")
         }
     }*/

    registerProductRoutesHttp(productService)
}