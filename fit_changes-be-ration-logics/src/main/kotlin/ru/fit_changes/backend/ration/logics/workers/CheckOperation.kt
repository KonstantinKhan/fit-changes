package ru.fit_changes.backend.ration.logics.workers

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.addCorWorkerDsl

fun ICorChain<BeContextRation>.checkOperationWorker(title: String, targetOperation: Operations) =
    addCorWorkerDsl {
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