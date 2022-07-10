package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.worker
import ru.fit_changes.backend.repo.product.DbProductIdRequest

fun ICorChain<BeContext>.repoRead(title: String) = worker {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = productRepo.read(DbProductIdRequest(requestProductId))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            dbProduct = resultValue
        } else {
            status = CorStatus.FAILING
            result.errors.forEach {
                addError(it)
            }
        }
    }
}