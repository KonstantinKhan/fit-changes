package ru.fit_changes.backend.app.ktor.product.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.fit_changes.backend.app.ktor.product.configs.KtorAuthConfig
import java.util.*

fun KtorAuthConfig.Companion.testUserToken(authConfig: KtorAuthConfig, year: Int, vararg groups: String): String =
    testToken(authConfig, year, *groups)

private fun testToken(authConfig: KtorAuthConfig, year: Int, vararg groups: String) = JWT.create()
    .withExpiresAt(
        GregorianCalendar().apply {
            set(year, 0, 1, 0, 0, 0)
            timeZone = TimeZone.getTimeZone("UTC")
        }.time
    )
    .withAudience(authConfig.audience)
    .withIssuer(authConfig.issuer)
    .withArrayClaim(KtorAuthConfig.GROUPS_CLAIM, groups)
    .sign(Algorithm.HMAC256(authConfig.secret))
    .apply {
        println("Test JWT token: $this")
    }
