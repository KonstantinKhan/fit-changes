package ru.fit_changes.backend.app.ktor.product.mappers

import io.ktor.server.auth.jwt.*
import ru.fit_changes.backend.common.models.BePrincipalModel
import ru.fit_changes.backend.common.models.enums.BeUserGroups

fun JWTPrincipal?.toModel() = this?.run {
    BePrincipalModel(
        groups = payload.getClaim("groups")
            ?.asList(String::class.java)
            ?.mapNotNull {
                try {
                    BeUserGroups.valueOf(it)
                } catch (t: Throwable) {
                    null
                }
            }?.toSet() ?: emptySet()
    )
} ?: BePrincipalModel.NONE