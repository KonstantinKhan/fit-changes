package ru.fit_changes.backend.ration.logics.workers

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.addCorWorkerDsl

fun CorChainDsl<BeContextRation>.chainInitWorker(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.NONE
    }
    handle {
        status = CorStatus.RUNNING
    }
}

