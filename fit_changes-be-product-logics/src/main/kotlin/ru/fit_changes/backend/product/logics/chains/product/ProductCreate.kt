package ru.fit_changes.backend.product.logics.chains.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.product.logics.ICorExecutor
import ru.fit_changes.backend.product.logics.chain
import ru.fit_changes.backend.product.logics.chains.stub.productCreateStub
import ru.fit_changes.backend.product.logics.workers.chainInitWorker

object ProductCreate : ICorExecutor<BeContext> by chain<BeContext>({
    chainInitWorker("Initialization of chain")
    productCreateStub("Processing of stub case CREATE")
}).build()