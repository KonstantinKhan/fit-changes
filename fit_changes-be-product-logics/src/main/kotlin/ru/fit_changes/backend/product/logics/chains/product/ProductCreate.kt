package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.cor.ICorExecutor
import ru.fit_changes.cor.chain
import ru.fit_changes.backend.product.logics.chains.stub.productCreateStub
import ru.fit_changes.cor.addCorWorkerDsl
import ru.fit_changes.backend.product.logics.workers.*
import ru.fit_changes.backend.product.logics.workers.chooseDb

object ProductCreate : ICorExecutor<BeContext> by chain<BeContext>({
    checkOperationWorker("Checking operation", Operations.CREATE)
    chainInitWorker("Initialization of chain")
    chooseDb(title = "Choose DB or STUB")
    productCreateStub("Processing of stub case CREATE")
    permissionsCalculation("Calculating permissions ")
    addCorWorkerDsl {
        this.title = "dbProduct init"
        on {
            status == CorStatus.RUNNING
        }
        handle {
            dbProduct.authorId = principal.authorId
        }
    }
    accessCalculation("Calculating access")
    prepareProductForSaving("Prepare product for saving")
    repoCreate("Write to DB")
    prepareResult("Preparing the result for sending")
    frontPermissions("Calculating user permissions for sending to frontend")
    prepareResponse("Preparing a response")
}).build()