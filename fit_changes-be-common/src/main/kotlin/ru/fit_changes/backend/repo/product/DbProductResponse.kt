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
    constructor(result: ProductModel, isSuccess: Boolean) : this(result, isSuccess, emptyList())
    constructor(error: CommonErrorModel) : this(null, false, listOf(error))
    constructor(e: Exception) : this(null, false, listOf(CommonErrorModel(e)))
}