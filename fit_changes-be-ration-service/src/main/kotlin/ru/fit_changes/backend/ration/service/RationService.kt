package ru.fit_changes.backend.ration.service

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.mapping.ration.*
import ru.fit_changes.backend.ration.logics.RationCrud
import ru.fit_changes.openapi.models.*

class RationService(
    private val crud: RationCrud
) {

    suspend fun createRation(context: BeContextRation, request: CreateRationRequest): CreateRationResponse {
        context.setQuery(request)
        crud.create(context)
        return context.toCreateRationResponse()
    }

    suspend fun readRation(context: BeContextRation, request: ReadRationRequest): ReadRationResponse {
        context.setQuery(request)
        crud.read(context)
        return context.toReadRationResponse()
    }

    suspend fun updateRation(context: BeContextRation, request: UpdateRationRequest): UpdateRationResponse {
        context.setQuery(request)
        crud.update(context)
        return context.toUpdateRationResponse()
    }

    suspend fun deleteRation(context: BeContextRation, request: DeleteRationRequest): DeleteRationResponse {
        context.setQuery(request)
        crud.delete(context)
        return context.toDeleteRationResponse()
    }

    suspend fun searchRation(context: BeContextRation, request: SearchRationRequest): SearchRationResponse {
        context.setQuery(request)
        crud.search(context)
        return context.toSearchRationResponse()
    }
}