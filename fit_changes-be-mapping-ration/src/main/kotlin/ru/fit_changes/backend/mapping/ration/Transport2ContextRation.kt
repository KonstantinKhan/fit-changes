package ru.fit_changes.backend.mapping.ration

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.common.mappers.toModel
import ru.fit_changes.backend.common.models.ration.*
import ru.fit_changes.openapi.models.*

fun BeContextRation.setQuery(query: CreateRationRequest) = apply {
    operation = Operations.CREATE
    requestId = query.requestId ?: ""
    requestRation = query.createRation?.toModel() ?: RationModel()
}

fun BeContextRation.setQuery(query: ReadRationRequest) = apply {
    operation = Operations.READ
    requestId = query.requestId ?: ""
    requestRationId = query.readRationId?.let { RationIdModel(it) } ?: RationIdModel.NONE
}

fun BeContextRation.setQuery(query: UpdateRationRequest) = apply {
    operation = Operations.UPDATE
    requestId = query.requestId ?: ""
    requestRation = query.updateRation?.toModel() ?: RationModel()
}

fun BeContextRation.setQuery(query: DeleteRationRequest) = apply {
    operation = Operations.DELETE
    requestId = query.requestId ?: ""
    requestRationId = query.deleteRationId?.let { RationIdModel(it) } ?: RationIdModel.NONE
}

fun BeContextRation.setQuery(query: SearchRationRequest) = apply {
    operation = Operations.SEARCH
    requestId = query.requestId ?: ""
    requestRationFilter = query.query?.let { RationSearchFilter(searchString = it) } ?: RationSearchFilter()
}