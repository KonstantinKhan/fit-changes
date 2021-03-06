package ru.fit_changes.backend.product.logics.utils

import ru.fit_changes.backend.common.models.BePrincipalModel
import ru.fit_changes.backend.common.models.enums.BeUserGroups
import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.utils.product.AUTHOR_ID_0001

fun testUser(): BePrincipalModel = BePrincipalModel(
    authorId = AuthorIdModel(id = AUTHOR_ID_0001),
    firstName = "Konstantin",
    lastName = "Khan",
    groups = setOf(
        BeUserGroups.USER,
        BeUserGroups.TEST
    )
)

fun testUserBan(): BePrincipalModel = BePrincipalModel(
    authorId = AuthorIdModel(id = "aID:0001"),
    firstName = "Konstantin",
    lastName = "Khan",
    groups = setOf(
        BeUserGroups.USER,
        BeUserGroups.TEST,
        BeUserGroups.BAN
    )
)