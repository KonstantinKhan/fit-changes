package ru.fit_changes.backend.product.logics.chains.stub

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.StubCases
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.product.logics.handlers.CorChainDsl
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl
import ru.fit_changes.backend.product.logics.handlers.chain
import ru.fit_changes.backend.product.logics.workers.noMatchingStubs
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL
import ru.fit_changes.backend.utils.product.PRODUCT_ID_0001
import ru.fit_changes.backend.utils.product.PRODUCT_ID_0002

fun CorChainDsl<BeContext>.productSearchStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING
                &&
                stubCase != StubCases.NONE
    }
    addCorWorkerDsl {
        this.title = "Success stubCase for SEARCH"
        on {
            status == CorStatus.RUNNING
                    &&
                    stubCase == StubCases.SUCCESS
        }
        handle {
            foundProducts.add(BEEF_FILLED_MODEL.copy(productId = ProductIdModel(PRODUCT_ID_0001)))
            foundProducts.add(BEEF_FILLED_MODEL.copy(productId = ProductIdModel(PRODUCT_ID_0002)))
            status = CorStatus.FINISHING
        }
    }
    noMatchingStubs()
}