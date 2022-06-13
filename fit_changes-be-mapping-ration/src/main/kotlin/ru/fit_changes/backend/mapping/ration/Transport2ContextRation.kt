package ru.fit_changes.backend.mapping.ration

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.mappers.toModel
import ru.fit_changes.backend.common.models.ration.*
import ru.fit_changes.openapi.models.*

fun BeContextRation.setQuery(query: CreateRationRequest) = apply {
    requestId = query.requestId ?: ""
    requestRation = query.createRation?.toModel() ?: RationModel()
}

fun BeContextRation.setQuery(query: ReadRationRequest) = apply {
    requestId = query.requestId ?: ""
    requestRationId = query.readRationId?.let { RationIdModel(it) } ?: RationIdModel.NONE
}

fun BeContextRation.setQuery(query: UpdateRationRequest) = apply {
    requestId = query.requestId ?: ""
    requestRation = query.updateRation?.toModel() ?: RationModel()
}

fun BeContextRation.setQuery(query: DeleteRationRequest) = apply {
    requestId = query.requestId ?: ""
    requestRationId = query.deleteRationId?.let { RationIdModel(it) } ?: RationIdModel.NONE
}

fun BeContextRation.setQuery(query: SearchRationRequest) = apply {
    requestId = query.requestId ?: ""
    requestRationFilter = query.query?.let { RationSearchFilter(searchString = it) } ?: RationSearchFilter()
}