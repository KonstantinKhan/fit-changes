package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker

fun CorChainDsl<BeContext>.chainInitWorker(title: String) = worker {
    this.title = title
    on {
        status == CorStatus.NONE
    }
    handle {
        status = CorStatus.RUNNING
    }
}

