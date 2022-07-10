package ru.fit_changes.backend.ration.repo.test

import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.utils.product.RATION_FILLED_MODEL
import java.util.*

abstract class BaseInitRation : IInitObjects<RationModel> {
    fun createInitTestModel() = RATION_FILLED_MODEL.copy(
        rationId = RationIdModel(UUID.randomUUID()),
        authorId = AuthorIdModel(UUID.randomUUID())
    )
}