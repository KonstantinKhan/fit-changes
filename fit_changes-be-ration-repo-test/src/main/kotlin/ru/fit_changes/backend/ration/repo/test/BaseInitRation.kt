package ru.fit_changes.backend.ration.repo.test

import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.utils.product.RATION_FILLED_MODEL

abstract class BaseInitRation : IInitObjects<RationModel> {
    fun createInitTestModel(
        suf: String
    ) = RATION_FILLED_MODEL
}