package ru.fit_changes.backend.repo.ration

import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.repo.IDbRequest

class DbRationModelRequest(
    val ration: RationModel
) : IDbRequest