package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.models.WorkMode
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker
import ru.fit_changes.backend.repo.product.IRepoProduct

internal fun CorChainDsl<BeContext>.chooseDb(title: String) = worker {
    this.title = title
    handle {
        productRepo = when (workMode) {
            WorkMode.PROD -> config.repoProductProd
            WorkMode.TEST -> config.repoProductTest
            WorkMode.STUB -> {
                IRepoProduct.NONE
            }
        }
    }
}