package ru.fit_changes.cor

fun <T> ICorChain<T>.worker(function: CorWorkerDsl<T>.() -> Unit) {
    add(CorWorkerDsl<T>().apply(function))
}

fun <T> ICorChain<T>.worker(
    title: String,
    description: String = "",
    function: suspend T.() -> Unit
) {
    add(
        CorWorkerDsl(
            title = title,
            description = description,
            blockHandle = function
        )
    )
}

class CorWorkerDsl<T>(
    override var title: String = "",
    override var description: String = "",
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