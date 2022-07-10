package ru.fit_changes.backend.ration.logics

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.RationContextConfig
import ru.fit_changes.backend.ration.logics.chains.*

class RationCrud(private val config: RationContextConfig = RationContextConfig()) {
    suspend fun create(context: BeContextRation) {
        RationCreate.exec(context.initSettings())
    }

    suspend fun read(context: BeContextRation) {
        RationRead.exec(context.initSettings())
    }

    suspend fun update(context: BeContextRation) {
        RationUpdate.exec(context.initSettings())
    }

    suspend fun delete(context: BeContextRation) {
        RationDelete.exec(context.initSettings())
    }

    suspend fun search(context: BeContextRation) {
        RationSearch.exec(context.initSettings())
    }

    private fun BeContextRation.initSettings() = apply {
        config = this@RationCrud.config
    }
}