package ru.fit_changes.backend.repo.product

import ru.fit_changes.backend.repo.IDbRequest

data class DbProductFilterRequest(
    val query: String = ""
): IDbRequest