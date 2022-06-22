package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.addCorWorkerDsl

fun CorChainDsl<BeContext>.repoAll(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = productRepo.allProducts()
        val resultValues = result.result
        if (result.isSuccess && !resultValues.isNullOrEmpty()) {
            foundProducts = resultValues.toMutableList()
        } else {
            result.errors.forEach { addError(it) }
        }
    }
}