package ru.fit_changes.backend.ration.logics.stubs

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.utils.product.RATION_FILLED_MODEL
import ru.fit_changes.backend.utils.product.RATION_ID
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.addCorWorkerDsl

fun CorChainDsl<BeContextRation>.rationSearchStub(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.RUNNING &&
                stubCase == StubCases.SUCCESS
    }
    handle {
        foundRations = mutableListOf(RATION_FILLED_MODEL.copy(rationId = RationIdModel(RATION_ID)))
    }
}