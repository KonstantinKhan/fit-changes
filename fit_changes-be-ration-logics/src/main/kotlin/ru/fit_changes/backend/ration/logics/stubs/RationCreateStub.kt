package ru.fit_changes.backend.ration.logics.stubs

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.utils.product.RATION_ID
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.addCorWorkerDsl
import ru.fit_changes.cor.chain

fun CorChainDsl<BeContextRation>.rationCreateStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING &&
                stubCase != StubCases.NONE
    }
    addCorWorkerDsl {
        this.title = "Successful stub case CREATE"
        on {
            stubCase == StubCases.SUCCESS
        }
        handle {
            responseRation = requestRation.copy(
                rationId = RationIdModel(RATION_ID)
            )
        }
    }
}