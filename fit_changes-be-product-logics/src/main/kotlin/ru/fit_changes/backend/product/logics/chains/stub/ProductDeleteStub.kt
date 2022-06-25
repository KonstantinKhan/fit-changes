package ru.fit_changes.backend.product.logics.chains.stub

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.addCorWorkerDsl
import ru.fit_changes.cor.chain
import ru.fit_changes.backend.product.logics.workers.noMatchingStubs
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL

fun CorChainDsl<BeContext>.productDeleteStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING
                &&
                stubCase != StubCases.NONE
    }
    addCorWorkerDsl {
        this.title = "Successful stubCase for DELETE"
        on {
            status == CorStatus.RUNNING
                    &&
                    stubCase == StubCases.SUCCESS
        }
        handle {
            responseProduct = BEEF_FILLED_MODEL.copy(productId = requestProductId)
            status = CorStatus.FINISHING
        }
    }
    noMatchingStubs()
}