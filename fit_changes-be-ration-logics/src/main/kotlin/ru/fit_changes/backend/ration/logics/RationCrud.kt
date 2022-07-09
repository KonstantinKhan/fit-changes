package ru.fit_changes.backend.ration.logics

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.RationContextConfig
import ru.fit_changes.backend.ration.logics.chains.*

class RationCrud(private val config: RationContextConfig = RationContextConfig()) {
    suspend fun create(context: BeContextRation) {
        RationCreate.exec(context.initSettings())
    }

    suspend fun read(context: BeContextRation) {
        RationRead.exec(context)
    }

    suspend fun update(context: BeContextRation) {
        RationUpdate.exec(context)
    }

    suspend fun delete(context: BeContextRation) {
        RationDelete.exec(context)
    }

    suspend fun search(context: BeContextRation) {
        RationSearch.exec(context)
    }

    private fun BeContextRation.initSettings() = apply {
        config = this@RationCrud.config
    }
}