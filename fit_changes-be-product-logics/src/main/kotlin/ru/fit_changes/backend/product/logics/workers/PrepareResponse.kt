package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.worker
import ru.fit_changes.cor.chain

fun ICorChain<BeContext>.prepareResponse(title: String) = chain {
    this.title = title
    worker {
        on {
            status in setOf(CorStatus.RUNNING, CorStatus.FINISHING)
        }
        handle {
            status = CorStatus.SUCCESS
        }
    }

    worker {
        on {
            status != CorStatus.SUCCESS
        }
        handle {
            status = CorStatus.ERROR
        }
    }
}