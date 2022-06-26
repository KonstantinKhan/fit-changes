package ru.fit_changes.backend.repo.ration

import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.repo.IDbResponse

class DbRationResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
    override val result: RationModel?

): IDbResponse<RationModel>