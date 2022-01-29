package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.product.logics.handlers.CorChainDsl
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl

fun CorChainDsl<BeContext>.chainInitWorker(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.NONE
    }
    handle {
        status = CorStatus.RUNNING
    }
}

