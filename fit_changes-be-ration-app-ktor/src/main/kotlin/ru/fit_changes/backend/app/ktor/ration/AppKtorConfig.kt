package ru.fit_changes.backend.app.ktor.ration

import ru.fit_changes.backend.common.context.RationContextConfig


data class AppKtorConfig(
    val contextConfig: RationContextConfig = RationContextConfig()
)
