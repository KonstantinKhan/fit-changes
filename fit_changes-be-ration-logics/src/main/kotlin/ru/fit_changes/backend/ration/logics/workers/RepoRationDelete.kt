package ru.fit_changes.backend.ration.logics.workers

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.repo.ration.DbRationIdRequest
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker

fun CorChainDsl<BeContextRation>.repoRationDelete(title: String) = worker {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = rationRepo.delete(DbRationIdRequest(requestRationId))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            responseRation = resultValue
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}