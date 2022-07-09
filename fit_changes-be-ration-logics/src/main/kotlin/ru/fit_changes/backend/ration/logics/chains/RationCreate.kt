package ru.fit_changes.backend.ration.logics.chains

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.ration.logics.stubs.rationCreateStub
import ru.fit_changes.backend.ration.logics.workers.chainInitWorker
import ru.fit_changes.backend.ration.logics.workers.checkOperationWorker
import ru.fit_changes.backend.ration.logics.workers.chooseDb
import ru.fit_changes.backend.ration.logics.workers.repoRationCreate
import ru.fit_changes.cor.ICorExecutor
import ru.fit_changes.cor.chain

object RationCreate : ICorExecutor<BeContextRation> by chain({
    checkOperationWorker("Checking operation", Operations.CREATE)
    chainInitWorker("Chain initialization")
    chooseDb("Choose DB or STUB")
    rationCreateStub("Processing of stub case CREATE")
    repoRationCreate("Write object in DB")
}).build()