package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.product.logics.handlers.CorChainDsl
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl


internal fun CorChainDsl<BeContext>.noMatchingStubs() = addCorWorkerDsl {
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