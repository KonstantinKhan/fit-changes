package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.worker
import ru.fit_changes.backend.repo.product.DbProductModelRequest

fun ICorChain<BeContext>.repoUpdate(title: String) = worker {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = productRepo.update(DbProductModelRequest(requestProduct))
        val resultValue = result.result
        if (resultValue != null && result.isSuccess) {
            dbProduct = resultValue
        } else {
            status = CorStatus.FAILING
            result.errors.forEach {
                addError(it)
            }
        }
    }
}