package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker
import ru.fit_changes.backend.repo.product.DbProductFilterRequest

fun CorChainDsl<BeContext>.repoSearch(title: String) = worker {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = productRepo.search(DbProductFilterRequest(requestProductFilter.searchStr))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            foundProducts = resultValue as MutableList<ProductModel>
        } else {
            status = CorStatus.FAILING
            result.errors.forEach { addError(it) }
        }
    }
}