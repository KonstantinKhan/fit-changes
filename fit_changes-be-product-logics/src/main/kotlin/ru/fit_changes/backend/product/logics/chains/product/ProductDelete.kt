package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.product.logics.ICorExecutor
import ru.fit_changes.backend.product.logics.chain
import ru.fit_changes.backend.product.logics.chains.stub.productDeleteStub
import ru.fit_changes.backend.product.logics.workers.chainInitWorker
import ru.fit_changes.backend.product.logics.workers.chooseDb
import ru.fit_changes.backend.product.logics.workers.repoDelete

object ProductDelete : ICorExecutor<BeContext> by chain<BeContext>({
    chainInitWorker("Initialization of chain")
    chooseDb(title = "Choose DB or STUB")
    productDeleteStub("Processing of stub case DELETE")
    repoDelete("Delete from DB")
}).build()