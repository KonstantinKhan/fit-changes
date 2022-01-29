package ru.fit_changes.backend.product.logics.handlers

import ru.fit_changes.backend.product.logics.*

fun <T> ICorChain<T>.addCorWorkerDsl(function: CorWorkerDsl<T>.() -> Unit) {
    add(CorWorkerDsl<T>().apply(function))
}

class CorWorkerDsl<T>(
    override var title: String = "",
    override val description: String = "",
    private var blockHandle: suspend T.() -> Unit = {}

) : ICorHandler<T>, CorComponentDsl<T>() {
    override fun handle(function: suspend T.() -> Unit) {
        this.blockHandle = function
    }

    override fun build(): ICorExecutor<T> = CorWorker(
        title = title,
        description = description,
        blockHandle = blockHandle,
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}

class CorWorker<T>(
    override val title: String,
    override val description: String,
    private val blockHandle: suspend T.() -> Unit,
    override val blockOn: T.() -> Boolean,
    override val blockExcept: T.(t: Throwable) -> Unit
) : AbstractWorker<T>(blockOn, blockExcept) {
    override suspend fun work(context: T) {
        blockHandle(context)
    }
}