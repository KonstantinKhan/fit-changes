package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.product.logics.handlers.CorChainDsl
import ru.fit_changes.backend.product.logics.handlers.addCorWorkerDsl
import ru.fit_changes.backend.repo.cassandra.CassandraObject.createRepo
import ru.fit_changes.backend.repo.product.DbProductModelRequest

fun CorChainDsl<BeContext>.repoCreate(title: String) = addCorWorkerDsl {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val repo = createRepo()
        val result = repo.create(
            DbProductModelRequest(
                ProductModel(
                    productName = responseProduct.productName,
                    caloriesPerHundredGrams = responseProduct.caloriesPerHundredGrams,
                    proteinsPerHundredGrams = responseProduct.proteinsPerHundredGrams,
                    fatsPerHundredGrams = responseProduct.fatsPerHundredGrams,
                    carbohydratesPerHundredGrams = responseProduct.carbohydratesPerHundredGrams,
                )
            )
        )
        val resultValue = result.result
        if (resultValue != null) {
            dbProduct = resultValue
            responseProduct = dbProduct
        }
    }
}