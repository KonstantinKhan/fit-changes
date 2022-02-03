package ru.fit_changes.backend.app.ktor.product

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fit_changes.backend.app.ktor.product.plugins.kafka
import ru.fit_changes.backend.repo.cassandra.CassandraObject.createRepo

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(
    testing: Boolean = false,
) {

    val repo = createRepo()

    routing {

        kafka()

        get("/") {
            call.respondText("Hello, World")
        }
    
    }
}