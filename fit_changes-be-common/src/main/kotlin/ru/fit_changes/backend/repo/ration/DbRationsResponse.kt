package ru.fit_changes.backend.repo.ration

import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.repo.IDbResponse

data class DbRationsResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel>,
    override val result: List<RationModel>?,
) : IDbResponse<List<RationModel>>
