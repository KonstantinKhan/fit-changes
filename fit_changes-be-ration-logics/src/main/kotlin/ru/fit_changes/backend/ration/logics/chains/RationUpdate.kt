package ru.fit_changes.backend.ration.logics.chains

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.ration.logics.stubs.rationUpdateStub
import ru.fit_changes.backend.ration.logics.workers.chainInitWorker
import ru.fit_changes.backend.ration.logics.workers.checkOperationWorker
import ru.fit_changes.backend.ration.logics.workers.chooseDb
import ru.fit_changes.backend.ration.logics.workers.repoUpdateRation
import ru.fit_changes.cor.ICorExecutor
import ru.fit_changes.cor.chain

object RationUpdate : ICorExecutor<BeContextRation> by chain<BeContextRation>({
    checkOperationWorker("Checking operation", Operations.UPDATE)
    chainInitWorker("Chain initialize")
    chooseDb("Choose DB or STUB")
    rationUpdateStub("Processing of stub case UPDATE")
    repoUpdateRation("Updated object in DB")
}).build()