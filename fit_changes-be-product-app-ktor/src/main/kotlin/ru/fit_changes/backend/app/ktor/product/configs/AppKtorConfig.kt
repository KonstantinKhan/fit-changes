package ru.fit_changes.backend.app.ktor.product.configs

import ru.fit_changes.backend.common.context.ContextConfig
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.repo.cassandra.CassandraObject
import ru.fit_changes.backend.repo.inmemory.RepoProductInMemory
import ru.fit_changes.backend.repo.product.IRepoProduct
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL
import ru.fit_changes.backend.utils.product.CHICKEN_FILLED_MODEL
import ru.fit_changes.backend.utils.product.CHICKEN_THIGH_FILLED_MODEL
import java.util.*

data class AppKtorConfig(
    val contextConfig: ContextConfig = ContextConfig(
        repoProductTest = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(productId = ProductIdModel(UUID.randomUUID())),
                CHICKEN_FILLED_MODEL.copy(productId = ProductIdModel(UUID.randomUUID())),
                CHICKEN_THIGH_FILLED_MODEL.copy(productId = ProductIdModel(UUID.randomUUID()))
            )
        ),
        repoProductProd = try {
            CassandraObject.createRepo()
        } catch (e: Exception) {
            IRepoProduct.NONE
        }
    )
)