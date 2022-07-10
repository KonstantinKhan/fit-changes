package ru.fit_changes.backend.ration.logics.chains

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.ration.logics.stubs.rationReadStub
import ru.fit_changes.backend.ration.logics.workers.*
import ru.fit_changes.cor.ICorExecutor
import ru.fit_changes.cor.chain

object RationRead : ICorExecutor<BeContextRation> by chain<BeContextRation>({
    checkOperationWorker("Checking operation", Operations.READ)
    chainInitWorker("Chain initialization")
    chooseDb("Choose DB or STUB")
    rationReadStub("Processing of stub case READ")
    repoRationRead("Read from DB")
}).build()