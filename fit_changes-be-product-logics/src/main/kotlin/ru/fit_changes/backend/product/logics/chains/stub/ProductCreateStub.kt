package ru.fit_changes.backend.product.logics.chains.stub

import ru.fit_changes.backend.utils.product.PRODUCT_ID_0001
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker
import ru.fit_changes.cor.chain
import ru.fit_changes.backend.product.logics.workers.noMatchingStubs
import ru.fit_changes.backend.utils.product.AUTHOR_ID_0001

internal fun CorChainDsl<BeContext>.productCreateStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING
                &&
                stubCase != StubCases.NONE
    }

    worker {
        this.title = "Successful stubCase for CREATE"
        on {
            status == CorStatus.RUNNING
                    &&
                    stubCase == StubCases.SUCCESS
        }
        handle {
            responseProduct = requestProduct.copy(
                productId = ProductIdModel(PRODUCT_ID_0001),
                authorId = AuthorIdModel(AUTHOR_ID_0001)
            )
            status = CorStatus.FINISHING
        }
    }
    noMatchingStubs()
}