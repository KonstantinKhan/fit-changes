package ru.fit_changes.backend.product.logics

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.ContextConfig
import ru.fit_changes.backend.product.logics.chains.product.*

class ProductCrud(private val config: ContextConfig = ContextConfig()) {
    suspend fun create(context: BeContext) {
        ProductCreate.exec(context.initSettings())
    }

    suspend fun read(context: BeContext) {
        ProductRead.exec(context.initSettings())
    }

    suspend fun update(context: BeContext) {
        ProductUpdate.exec(context.initSettings())
    }

    suspend fun delete(context: BeContext) {
        ProductDelete.exec(context.initSettings())
    }

    suspend fun search(context: BeContext) {
        ProductSearch.exec(context.initSettings())
    }

    suspend fun allProducts(context: BeContext) {
        ProductAll.exec(context.initSettings())
    }

    private fun BeContext.initSettings() = apply {
        config = this@ProductCrud.config
    }
}