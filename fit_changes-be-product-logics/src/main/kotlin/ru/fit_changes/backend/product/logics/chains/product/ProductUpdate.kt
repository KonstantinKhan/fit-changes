package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.cor.ICorExecutor
import ru.fit_changes.cor.chain
import ru.fit_changes.backend.product.logics.chains.stub.productUpdateStub
import ru.fit_changes.cor.worker
import ru.fit_changes.backend.product.logics.workers.*
import ru.fit_changes.backend.product.logics.workers.chooseDb

object ProductUpdate : ICorExecutor<BeContext> by chain<BeContext>({
    checkOperationWorker("Checking operation", Operations.UPDATE)
    chainInitWorker("Initialization of chain")
    chooseDb(title = "Choose DB or STUB")
    productUpdateStub(title = "Processing of stub case UPDATE")
    permissionsCalculation("Calculating permissions")
    worker("Init requestProductId") { requestProductId = requestProduct.productId }
    repoRead("Read from DB")
    accessCalculation("Calculating access")
    prepareProductForSaving("Prepare product for saving")
    repoUpdate("Updating product in DB")
    prepareResult("Preparing the result for sending")
    frontPermissions("Calculating user permissions for sending to frontend")
    prepareResponse("Preparing a response")
}).build()