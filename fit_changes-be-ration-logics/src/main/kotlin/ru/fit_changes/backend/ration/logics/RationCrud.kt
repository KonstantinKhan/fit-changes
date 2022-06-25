package ru.fit_changes.backend.ration.logics

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.ration.logics.chains.*

class RationCrud() {
    suspend fun create(context: BeContextRation) {
        RationCreate.exec(context)
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
}