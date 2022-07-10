package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.worker

fun ICorChain<BeContext>.checkOperationWorker(title: String, targetOperation: Operations) =
    worker {
        this.title = title
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