package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.addCorWorkerDsl
import ru.fit_changes.backend.repo.product.DbProductIdRequest

fun CorChainDsl<BeContext>.repoDelete(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = productRepo.delete(DbProductIdRequest(requestProductId))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            dbProduct = resultValue
        } else {
            status = CorStatus.FAILING
            result.errors.forEach { addError(it) }
        }
    }
}