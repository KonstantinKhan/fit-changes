package ru.fit_changes.backend.app.ktor.ration.controllers

import io.ktor.server.application.*
import ru.fit_changes.backend.app.ktor.ration.helpers.handleRoutes
import ru.fit_changes.backend.ration.service.RationService
import ru.fit_changes.openapi.models.CreateRationRequest
import ru.fit_changes.openapi.models.CreateRationResponse
import ru.fit_changes.openapi.models.ReadRationRequest
import ru.fit_changes.openapi.models.ReadRationResponse

suspend fun ApplicationCall.createRation(rationService: RationService) {
    handleRoutes<CreateRationRequest, CreateRationResponse> { request ->
        rationService.createRation(this, request)
    }
}

suspend fun ApplicationCall.readRation(rationService: RationService) {
    handleRoutes<ReadRationRequest, ReadRationResponse> { request ->
        rationService.readRation(this, request)
    }
}