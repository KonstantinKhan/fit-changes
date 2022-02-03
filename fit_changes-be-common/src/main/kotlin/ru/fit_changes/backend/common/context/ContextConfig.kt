package ru.fit_changes.backend.common.context

import ru.fit_changes.backend.repo.product.IRepoProduct

data class ContextConfig(
    val repoProductTest: IRepoProduct = IRepoProduct.NONE,
    val repoProductProd: IRepoProduct = IRepoProduct.NONE,
)
