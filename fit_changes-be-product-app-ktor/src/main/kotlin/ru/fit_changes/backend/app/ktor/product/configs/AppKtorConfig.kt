package ru.fit_changes.backend.app.ktor.product.configs

import io.ktor.server.application.*
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
    val testing: Boolean = true,
    val contextConfig: ContextConfig = ContextConfig(
        repoProductTest = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(productId = ProductIdModel(UUID.randomUUID())),
                CHICKEN_FILLED_MODEL.copy(productId = ProductIdModel(UUID.randomUUID())),
                CHICKEN_THIGH_FILLED_MODEL.copy(productId = ProductIdModel(UUID.randomUUID()))
            )
        ),
        repoProductProd = try {
            if (!testing) {
                CassandraObject.createRepo()
            } else {
                IRepoProduct.NONE
            }
        } catch (e: Exception) {
            IRepoProduct.NONE
        }
    ),
    var auth: KtorAuthConfig = KtorAuthConfig.TEST
) {
    constructor(environment: ApplicationEnvironment) : this(
        auth = KtorAuthConfig(environment)
    )

    constructor(testing: Boolean, environment: ApplicationEnvironment) : this(
        testing = true,
        auth = KtorAuthConfig(environment)
    )
}