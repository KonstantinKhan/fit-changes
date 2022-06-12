package ru.fit_changes.backend.common.context

import ru.fit_changes.backend.common.models.ration.RationModel

data class BeContextRation(
    var requestId: String = "",

    var requestRation: RationModel = RationModel()
)

