package ru.fit_changes.backend.product.logics

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.ContextConfig
import ru.fit_changes.backend.product.logics.chains.product.ProductCreate

class ProductCrud(val config: ContextConfig = ContextConfig()) {
    suspend fun create(context: BeContext) {
        ProductCreate.exec(context.initSettings())
    }

    private fun BeContext.initSettings() = apply {
        config = this@ProductCrud.config
    }
}