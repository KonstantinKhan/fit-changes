package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.product.logics.ICorExecutor
import ru.fit_changes.backend.product.logics.chain
import ru.fit_changes.backend.product.logics.chains.stub.productUpdateStub
import ru.fit_changes.backend.product.logics.workers.chainInitWorker
import ru.fit_changes.backend.product.logics.workers.chooseDb

object ProductUpdate : ICorExecutor<BeContext> by chain<BeContext>({
    chainInitWorker("Initialization of chain")
    chooseDb(title = "Choose DB or STUB")
    productUpdateStub(title = "Processing of stub case UPDATE")
}).build()