package ru.fit_changes.backend.app.ktor.product.configs

import io.ktor.server.application.*

data class KtorAuthConfig(
    val issuer: String,
    val audience: String,
    val realm: String
) {
    constructor(environment: ApplicationEnvironment) : this(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        realm = environment.config.property("jwt.realm").getString()
    )

    companion object {
        const val GROUPS_CLAIM = "groups"

        val TEST = KtorAuthConfig(
            issuer = "http://0.0.0.0:8080/",
            audience = "product-users",
            realm = "products"
        )
    }
}