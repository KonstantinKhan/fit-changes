package ru.fit_changes.backend.app.ktor.product.configs

import ru.fit_changes.backend.common.context.ContextConfig
import ru.fit_changes.backend.repo.inmemory.RepoProductInMemory

data class AppKtorConfig(
    val contextConfig: ContextConfig = ContextConfig(
        repoProductTest = RepoProductInMemory()
    )
)