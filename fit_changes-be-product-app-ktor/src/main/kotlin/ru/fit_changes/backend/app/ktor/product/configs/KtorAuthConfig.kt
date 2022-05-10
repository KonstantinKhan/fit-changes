package ru.fit_changes.backend.app.ktor.product.configs

import io.ktor.server.application.*

data class KtorAuthConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String
) {
    constructor(environment: ApplicationEnvironment) : this(
        secret = environment.config.property("jwt.secret").getString(),
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        realm = environment.config.property("jwt.realm").getString()
    )

    companion object {
        val TEST = KtorAuthConfig(
            secret = "secret",
            issuer = "issuer",
            audience = "audience",
            realm = "products"
        )
    }
}