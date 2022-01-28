package ru.fitChanges.backend.common.product.context

import common.models.CommonError
import common.models.IError
import ru.fitChanges.backend.common.product.models.ProductModel

data class BeContext(
    var requestId: String = "",
    var requestProduct: ProductModel = ProductModel(),
    var responseProduct: ProductModel = ProductModel(),
    var errors: MutableSet<IError> = mutableSetOf()
) {
    fun addError(field: String) {
        errors.add(CommonError(field))
    }
}