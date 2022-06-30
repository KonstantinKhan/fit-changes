package ru.fit_changes.backend.product.logics.chains.stub

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker
import ru.fit_changes.cor.chain
import ru.fit_changes.backend.product.logics.workers.noMatchingStubs

fun CorChainDsl<BeContext>.productUpdateStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING
                &&
                stubCase != StubCases.NONE
    }
    worker {
        this.title = "Successful stubCase for UPDATE"
        on {
            status == CorStatus.RUNNING
                    &&
                    stubCase == StubCases.SUCCESS
        }
        handle {
            responseProduct = ProductModel().apply {
                productId = requestProduct.productId
                productName = requestProduct.productName
                caloriesPerHundredGrams = requestProduct.caloriesPerHundredGrams
                proteinsPerHundredGrams = requestProduct.proteinsPerHundredGrams
                fatsPerHundredGrams = requestProduct.fatsPerHundredGrams
                carbohydratesPerHundredGrams = requestProduct.carbohydratesPerHundredGrams
            }
            status = CorStatus.FINISHING
        }
    }
    noMatchingStubs()
}