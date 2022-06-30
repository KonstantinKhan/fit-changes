package ru.fit_changes.backend.product.logics.chains.stub

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker
import ru.fit_changes.cor.chain
import ru.fit_changes.backend.product.logics.workers.noMatchingStubs
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL

fun CorChainDsl<BeContext>.productReadStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING
                &&
                stubCase != StubCases.NONE
    }
    worker {
        this.title = "Successful stubCase for READ"
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