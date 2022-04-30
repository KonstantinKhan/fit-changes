package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.product.logics.handlers.CorChainDsl
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl
import ru.fit_changes.backend.repo.product.DbProductIdRequest

fun CorChainDsl<BeContext>.repoDelete(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        println("status: $status")
        status == CorStatus.RUNNING
    }
    handle {
        println("handle start")
        val result = productRepo.delete(DbProductIdRequest(requestProductId))
        println("handle: $result")
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            responseProduct = resultValue
        } else {
            result.errors.forEach { addError(it) }
        }
    }
}