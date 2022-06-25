package ru.fit_changes.backend.ration.logics.chains

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.ration.logics.stubs.rationSearchStub
import ru.fit_changes.backend.ration.logics.workers.chainInitWorker
import ru.fit_changes.backend.ration.logics.workers.checkOperationWorker
import ru.fit_changes.cor.ICorExecutor
import ru.fit_changes.cor.chain

object RationSearch : ICorExecutor<BeContextRation> by chain<BeContextRation>({
    checkOperationWorker("Checking operation", Operations.SEARCH)
    chainInitWorker("Chain initialization")
    rationSearchStub("Processing of stub case SEARCH")
}).build()