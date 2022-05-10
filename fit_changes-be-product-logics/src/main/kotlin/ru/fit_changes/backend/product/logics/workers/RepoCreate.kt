package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.product.logics.handlers.CorChainDsl
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl
import ru.fit_changes.backend.repo.product.DbProductModelRequest

fun CorChainDsl<BeContext>.repoCreate(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = productRepo.create(DbProductModelRequest(dbProduct))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            dbProduct = resultValue
        } else {
            status = CorStatus.FAILING
            result.errors.forEach { addError(it) }
        }
    }
}