package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.worker

fun ICorChain<BeContext>.prepareProductForSaving(title: String) = worker {
    this.title = title
    description = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        with(dbProduct) {
            productName = requestProduct.productName
            caloriesPerHundredGrams = requestProduct.caloriesPerHundredGrams
            proteinsPerHundredGrams = requestProduct.proteinsPerHundredGrams
            fatsPerHundredGrams = requestProduct.fatsPerHundredGrams
            carbohydratesPerHundredGrams = requestProduct.carbohydratesPerHundredGrams
        }
    }
}