package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.cor.ICorExecutor
import ru.fit_changes.cor.chain
import ru.fit_changes.backend.product.logics.chains.stub.productSearchStub
import ru.fit_changes.backend.product.logics.workers.*
import ru.fit_changes.backend.product.logics.workers.chooseDb

object ProductSearch : ICorExecutor<BeContext> by chain<BeContext>({
    checkOperationWorker("Checking operation", Operations.SEARCH)
    chainInitWorker("Initialization of chain")
    chooseDb("Choose DB or STUB")
    productSearchStub("Processing of stub case SEARCH")
    permissionsCalculation("Calculating permissions")
    repoSearch("Search in DB")
    accessCalculation("Calculating access")
    prepareResponse("Preparing the response")
}).build()