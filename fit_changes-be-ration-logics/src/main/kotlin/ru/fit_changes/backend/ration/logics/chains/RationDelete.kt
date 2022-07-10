package ru.fit_changes.backend.ration.logics.chains

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.ration.logics.stubs.rationDeleteStub
import ru.fit_changes.backend.ration.logics.workers.chainInitWorker
import ru.fit_changes.backend.ration.logics.workers.checkOperationWorker
import ru.fit_changes.backend.ration.logics.workers.chooseDb
import ru.fit_changes.backend.ration.logics.workers.repoRationDelete
import ru.fit_changes.cor.ICorExecutor
import ru.fit_changes.cor.chain

object RationDelete : ICorExecutor<BeContextRation> by chain<BeContextRation>({
    checkOperationWorker("Checking operation", Operations.DELETE)
    chainInitWorker("Chain initialization")
    chooseDb("Choose DB or STUB")
    rationDeleteStub("Processing of stub case DELETE")
    repoRationDelete("Delete ration from DB")
}).build()