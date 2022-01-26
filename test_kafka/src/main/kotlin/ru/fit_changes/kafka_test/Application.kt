package ru.fit_changes.kafka_test

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fit_changes.kafka_test.plugins.kafka

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    routing {

        kafka()

        get("/") {
            call.respondText("Hello, World")
        }

    }
}