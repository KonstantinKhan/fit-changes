package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.product.logics.ICorExecutor
import ru.fit_changes.backend.product.logics.chain
import ru.fit_changes.backend.product.logics.chains.stub.productDeleteStub
import ru.fit_changes.backend.product.logics.workers.*
import ru.fit_changes.backend.product.logics.workers.chooseDb

object ProductDelete : ICorExecutor<BeContext> by chain<BeContext>({
    checkOperationWorker("Checking operation", Operations.DELETE)
    chainInitWorker("Initialization of chain")
    chooseDb(title = "Choose DB or STUB")
    productDeleteStub("Processing of stub case DELETE")
    permissionsCalculation("Calculating permissions")
    repoRead("Reading product from DB")
    accessCalculation("Calculating access")
    repoDelete("Delete from DB")
    prepareResult("Preparing a result for sending")
    frontPermissions("Calculating user permissions for sending to frontend")
    prepareResponse("Preparing the response")
}).build()