package ru.fit_changes.backend.common.models

import ru.fit_changes.backend.common.models.enums.BeUserGroups

data class BePrincipalModel(
    val authorId: AuthorIdModel = AuthorIdModel.NONE,
    val firstName: String = "",
    val lastName: String = "",
    val groups: Set<BeUserGroups> = emptySet(),
) {
    companion object {
        val NONE = BePrincipalModel()
    }
}