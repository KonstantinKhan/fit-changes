package ru.fit_changes.kafka_test

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
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