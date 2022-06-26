package ru.fit_changes.backend.repo.ration

import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.repo.IDbRequest

class DbRationIdRequest(
    val id: RationIdModel
) : IDbRequest