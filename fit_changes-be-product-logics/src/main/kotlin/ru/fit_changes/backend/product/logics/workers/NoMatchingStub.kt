package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker


internal fun CorChainDsl<BeContext>.noMatchingStubs() = worker {
    this.title = "No matching stubCase"
    on {
        status == CorStatus.RUNNING
    }
    handle {
        status = CorStatus.FAILING
        addError(
            CommonErrorModel(message = "No matching worker for stubCase: $stubCase")
        )
    }
}