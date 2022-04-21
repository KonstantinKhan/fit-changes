package ru.fit_changes.backend.repo.product

import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.IDbResponse

class DbProductsResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
    override val result: List<ProductModel>?
) : IDbResponse<List<ProductModel>>