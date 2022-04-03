package ru.fit_changes.backend.product.logics.handlers

import ru.fit_changes.backend.product.logics.*

fun <T> CorChainDsl<T>.chain(function: CorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>().apply(function))
}

class CorChainDsl<T>(
    private val builders: MutableList<ICorExecutorBuilder<T>> = mutableListOf(),
    override var title: String = "",
    override var description: String = ""

) : ICorChain<T>, CorComponentDsl<T>() {
    override fun add(builder: ICorExecutorBuilder<T>) {
        builders.add(builder)
    }

    override fun build(): ICorExecutor<T> = CorChain(
        title = title,
        description = description,
        executors = builders.map { it.build() },
        blockOn = blockOn,
        blockExcept = blockExcept

    )
}

class CorChain<T>(
    private val executors: List<ICorExecutor<T>>,
    override val title: String,
    override val description: String,
    override val blockOn: T.() -> Boolean,
    override val blockExcept: T.(t: Throwable) -> Unit

) : AbstractWorker<T>(blockOn, blockExcept) {
    override suspend fun work(context: T) {
        executors.forEach { it.exec(context) }
    }
}