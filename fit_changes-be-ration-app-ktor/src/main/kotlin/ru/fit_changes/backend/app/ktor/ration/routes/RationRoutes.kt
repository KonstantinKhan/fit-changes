package ru.fit_changes.backend.app.ktor.ration.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.fit_changes.backend.app.ktor.ration.controllers.createRation
import ru.fit_changes.backend.ration.service.RationService

fun Application.rationRoutes(rationService: RationService) {
    routing {
        route("ration") {
            post("create") {
                call.createRation(rationService)
            }
            post("read") {

            }
            post("updated") {

            }
            post("delete") {

            }
            post("search") {

            }
        }
    }
}