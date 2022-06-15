package ru.fit_changes.backend.common.context

import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.common.models.ration.RationSearchFilter

data class BeContextRation(
    var requestId: String = "",

    var requestRation: RationModel = RationModel(),
    var requestRationId: RationIdModel = RationIdModel.NONE,
    var responseRation: RationModel = RationModel(),
    var foundRations: List<RationModel> = mutableListOf(),

    var requestRationFilter: RationSearchFilter = RationSearchFilter(),

    val errors: MutableList<IError> = mutableListOf()
)

