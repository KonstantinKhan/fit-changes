package ru.fit_changes.backend.ration.service

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.mapping.ration.*
import ru.fit_changes.openapi.models.*

class RationService {

    fun createRation(context: BeContextRation, request: CreateRationRequest): CreateRationResponse {
        context.setQuery(request)
        return context.toCreateRationResponse()
    }

    fun readRation(context: BeContextRation, request: ReadRationRequest): ReadRationResponse {
        context.setQuery(request)
        return context.toReadRationResponse()
    }

    fun updateRation(context: BeContextRation, request: UpdateRationRequest): UpdateRationResponse {
        context.setQuery(request)
        return context.toUpdateRationResponse()
    }

    fun deleteRation(context: BeContextRation, request: DeleteRationRequest): DeleteRationResponse {
        context.setQuery(request)
        return context.toDeleteRationResponse()
    }

    fun searchRation(context: BeContextRation, request: SearchRationRequest): SearchRationResponse {
        context.setQuery(request)
        return context.toSearchRationResponse()
    }
}