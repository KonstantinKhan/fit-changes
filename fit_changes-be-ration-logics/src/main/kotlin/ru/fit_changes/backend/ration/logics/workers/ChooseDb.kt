package ru.fit_changes.backend.ration.logics.workers

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.models.WorkMode
import ru.fit_changes.backend.repo.ration.IRepoRation
import ru.fit_changes.cor.CorChainDsl
import ru.fit_changes.cor.worker

fun CorChainDsl<BeContextRation>.chooseDb(title: String) = worker {
    this.title = title
    handle {
        rationRepo = when (workMode) {
            WorkMode.PROD -> config.repoRationProd
            WorkMode.STUB -> IRepoRation.NONE
            WorkMode.TEST -> config.repoRationTest
        }
    }
}