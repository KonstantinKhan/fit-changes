package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.product.logics.ICorExecutor
import ru.fit_changes.backend.product.logics.chain
import ru.fit_changes.backend.product.logics.chains.stub.productSearchStub
import ru.fit_changes.backend.product.logics.workers.chainInitWorker
import ru.fit_changes.backend.product.logics.workers.chooseDb
import ru.fit_changes.backend.product.logics.workers.repoSearch

object ProductSearch : ICorExecutor<BeContext> by chain<BeContext>({
    chainInitWorker("Initialization of chain")
    chooseDb("Choose DB or STUB")
    productSearchStub("Processing of stub case SEARCH")
    repoSearch("Search in DB")
}).build()