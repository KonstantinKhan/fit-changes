package ru.fit_changes.backend.common.product.models

data class ProductSearchFilter(
    var searchStr: String = "",
    var authorId: AuthorIdModel = AuthorIdModel.NONE
)