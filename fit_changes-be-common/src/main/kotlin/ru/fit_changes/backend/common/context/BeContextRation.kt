package ru.fit_changes.backend.common.context

import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.common.models.ration.RationSearchFilter

data class BeContextRation(
    var requestId: String = "",
    var operation: Operations = Operations.NONE,
    var status: CorStatus = CorStatus.NONE,

    var stubCase: StubCases = StubCases.NONE,

    var requestRation: RationModel = RationModel(),
    var requestRationId: RationIdModel = RationIdModel.NONE,
    var responseRation: RationModel = RationModel(),
    var foundRations: List<RationModel> = mutableListOf(),

    var requestRationFilter: RationSearchFilter = RationSearchFilter(),

    val errors: MutableList<IError> = mutableListOf(),


    ) {

    fun addError(
        e: IError,
        failingStatus: Boolean = true
    ) {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(e)
    }

    fun addError(
        t: Throwable,
        level: IError.Level = IError.Level.ERROR
    ) {
        addError(CommonErrorModel(t))
    }
}

