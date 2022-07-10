package ru.fit_changes.backend.ration.logics.workers

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.repo.ration.DbRationFilterRequest
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker

fun CorChainDsl<BeContextRation>.repoRationSearch(title: String) = worker {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = rationRepo.search(
            DbRationFilterRequest(
                query = requestRationFilter.searchString
            )
        )
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            foundRations = resultValue
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}