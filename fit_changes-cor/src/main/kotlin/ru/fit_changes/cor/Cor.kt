package ru.fit_changes.cor

fun <T> chain(function: CorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply(function)

interface ICorChain<T> {
    fun add(builder: ICorExecutorBuilder<T>)
}

interface ICorHandler<T> {
    fun handle(function: suspend T.() -> Unit)
}

interface ICorExecutorBuilder<T> {
    val title: String
    val description: String
    fun build(): ICorExecutor<T>
}

interface ICorExecutor<T> {
    val title: String
    val description: String
    suspend fun exec(context: T)
}

interface ICorWorker<T> : ICorExecutor<T> {
    suspend fun on(context: T): Boolean
    suspend fun work(context: T)
    suspend fun except(context: T, t: Throwable)

    override suspend fun exec(context: T) {
        try {
            if (on(context)) {
                work(context)
            }
        } catch (t: Throwable) {
            except(context, t)
        }
    }
}

abstract class CorComponentDsl<T>(
    var blockOn: T.() -> Boolean = { true },
    var blockExcept: T.(t: Throwable) -> Unit = {}

) : ICorExecutorBuilder<T> {
    fun on(function: T.() -> Boolean) {
        blockOn = function
    }

    fun except(function: T.(t: Throwable) -> Unit) {
        blockExcept = function
    }
}

abstract class AbstractWorker<T>(
    open val blockOn: T.() -> Boolean,
    open val blockExcept: T.(t: Throwable) -> Unit

) : ICorWorker<T> {
    override suspend fun on(context: T): Boolean = blockOn(context)

    override suspend fun except(context: T, t: Throwable) = blockExcept(context, t)
}