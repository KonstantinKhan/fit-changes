package ru.fit_changes.backend.ration.logics.stubs

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.backend.utils.product.RATION_FILLED_MODEL
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker

fun CorChainDsl<BeContextRation>.rationReadStub(title: String) = worker {
    this.title = title
    on {
        stubCase == StubCases.SUCCESS &&
                status == CorStatus.RUNNING
    }

    handle {
        responseRation = RATION_FILLED_MODEL.copy(
            rationId = requestRationId
        )
    }
}