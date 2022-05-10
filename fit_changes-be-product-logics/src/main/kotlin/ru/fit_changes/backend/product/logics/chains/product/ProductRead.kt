package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.product.logics.ICorExecutor
import ru.fit_changes.backend.product.logics.chain
import ru.fit_changes.backend.product.logics.chains.stub.productReadStub
import ru.fit_changes.backend.product.logics.workers.*
import ru.fit_changes.backend.product.logics.workers.chooseDb

object ProductRead : ICorExecutor<BeContext> by chain<BeContext>({
    checkOperationWorker("Checking operation", Operations.READ)
    chainInitWorker("Initialization of chain")
    chooseDb(title = "Choose DB or STUB")
    productReadStub("Processing of stub case READ")
    permissionsCalculation("Calculating permissions")
    repoRead("Reading from db")
    accessCalculation("Calculating access")
    prepareResult("Preparing the result for sending")
    frontPermissions("Calculating user permissions for sending to frontend")
    prepareResponse("Preparing the response")
}).build()