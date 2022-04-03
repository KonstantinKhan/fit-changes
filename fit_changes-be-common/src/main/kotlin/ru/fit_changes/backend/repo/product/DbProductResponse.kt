package ru.fit_changes.backend.repo.product

import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.IDbResponse

data class DbProductResponse(
    override val result: ProductModel?,
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
) : IDbResponse<ProductModel?> {
    constructor(result: ProductModel) : this(result, true, emptyList())
    constructor(error: CommonErrorModel) : this(null, false, listOf(error))
}