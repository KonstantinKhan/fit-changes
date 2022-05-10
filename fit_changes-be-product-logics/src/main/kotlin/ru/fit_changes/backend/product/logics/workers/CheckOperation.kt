package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.product.logics.ICorChain
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl

fun ICorChain<BeContext>.checkOperationWorker(title: String, targetOperation: Operations) = addCorWorkerDsl {
    on {
        operation != targetOperation
    }
    handle {
        status = CorStatus.FAILING
        addError(
            t = Exception("Excepted $targetOperation but was $operation")
        )
    }
}