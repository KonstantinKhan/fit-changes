package ru.fit_changes.backend.app.ktor.product.mappers

import io.ktor.server.auth.jwt.*
import ru.fit_changes.backend.common.models.BePrincipalModel
import ru.fit_changes.backend.common.models.enums.BeUserGroups
import ru.fit_changes.backend.common.product.models.AuthorIdModel

fun JWTPrincipal?.toModel() = this?.run {
    BePrincipalModel(
        authorId = AuthorIdModel(payload.getClaim("sub").asString()),
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