package ru.fit_changes.backend.product.logics.chains.stub

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.StubCases
import ru.fit_changes.backend.product.logics.handlers.CorChainDsl
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl
import ru.fit_changes.backend.product.logics.workers.noMatchingStubs
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL

fun CorChainDsl<BeContext>.productReadStub(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.RUNNING
                &&
                stubCase == StubCases.SUCCESS
    }
    handle {
        responseProduct = BEEF_FILLED_MODEL.copy(productId = requestProductId)
        status = CorStatus.FINISHING
    }
    noMatchingStubs()
}