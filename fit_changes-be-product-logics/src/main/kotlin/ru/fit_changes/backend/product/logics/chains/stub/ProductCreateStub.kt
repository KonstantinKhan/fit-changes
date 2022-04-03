package ru.fit_changes.backend.product.logics.chains.stub

import ru.fit_changes.backend.utils.product.PRODUCT_ID_0001
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.StubCases
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.product.logics.handlers.CorChainDsl
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl

fun CorChainDsl<BeContext>.productCreateStub(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.RUNNING
                &&
                stubCase == StubCases.SUCCESS
    }
    handle {
        responseProduct = requestProduct.copy(productId = ProductIdModel(PRODUCT_ID_0001))
        status = CorStatus.FINISHING
    }
}