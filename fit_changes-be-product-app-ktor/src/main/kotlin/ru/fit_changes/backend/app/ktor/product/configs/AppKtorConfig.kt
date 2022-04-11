package ru.fit_changes.backend.app.ktor.product.configs

import ru.fit_changes.backend.common.context.ContextConfig
import ru.fit_changes.backend.repo.cassandra.CassandraObject
import ru.fit_changes.backend.repo.inmemory.RepoProductInMemory
import ru.fit_changes.backend.repo.product.IRepoProduct

data class AppKtorConfig(
    val contextConfig: ContextConfig = ContextConfig(
        repoProductTest = RepoProductInMemory(),
        repoProductProd = try {
            CassandraObject.createRepo()
        } catch (e: Exception) {
            IRepoProduct.NONE
        }
    )
)