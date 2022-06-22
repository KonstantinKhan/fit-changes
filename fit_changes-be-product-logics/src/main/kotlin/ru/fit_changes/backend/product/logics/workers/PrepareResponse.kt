package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.addCorWorkerDsl
import ru.fit_changes.cor.chain

fun ICorChain<BeContext>.prepareResponse(title: String) = chain {
    this.title = title
    addCorWorkerDsl {
        on {
            status in setOf(CorStatus.RUNNING, CorStatus.FINISHING)
        }
        handle {
            status = CorStatus.SUCCESS
        }
    }

    addCorWorkerDsl {
        on {
            status != CorStatus.SUCCESS
        }
        handle {
            status = CorStatus.ERROR
        }
    }
}