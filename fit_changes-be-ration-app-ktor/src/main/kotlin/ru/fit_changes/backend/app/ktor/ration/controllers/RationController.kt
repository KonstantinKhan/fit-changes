package ru.fit_changes.backend.app.ktor.ration.controllers

import io.ktor.server.application.*
import ru.fit_changes.backend.app.ktor.ration.helpers.handleRoutes
import ru.fit_changes.backend.ration.service.RationService
import ru.fit_changes.openapi.models.CreateRationRequest
import ru.fit_changes.openapi.models.CreateRationResponse

suspend fun ApplicationCall.createRation(rationService: RationService) {
    handleRoutes<CreateRationRequest, CreateRationResponse> {request ->
        rationService.createRation(this, request)
    }
}