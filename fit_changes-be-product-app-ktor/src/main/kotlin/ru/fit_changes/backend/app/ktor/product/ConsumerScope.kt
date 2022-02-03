package ru.fit_changes.backend.app.ktor.product

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ConsumerScope : CoroutineScope {

    private val parent: CompletableJob = Job()
    override val coroutineContext: CoroutineContext
        get() = parent
}