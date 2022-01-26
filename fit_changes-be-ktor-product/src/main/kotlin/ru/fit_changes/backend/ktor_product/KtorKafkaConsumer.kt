package ru.fit_changes.backend.ktor_product

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class KtorKafkaConsumer : CoroutineScope {

    private val parent: CompletableJob = Job()
    override val coroutineContext: CoroutineContext
        get() = parent
}