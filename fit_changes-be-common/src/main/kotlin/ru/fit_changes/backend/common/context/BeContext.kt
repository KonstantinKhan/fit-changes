package ru.fit_changes.backend.common.context

import ru.fit_changes.backend.common.models.StubCases
import ru.fit_changes.backend.common.models.CommonError
import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.backend.common.product.models.ProductModel

data class BeContext(

    var config: ContextConfig = ContextConfig(),

    var requestId: String = "",
    var operation: Operations = Operations.NONE,
    var stubCase: StubCases = StubCases.NONE,
    var status: CorStatus = CorStatus.NONE,

    var requestProduct: ProductModel = ProductModel(),
    var responseProduct: ProductModel = ProductModel(),
    var errors: MutableSet<IError> = mutableSetOf()
) {

    fun addError(field: String) {
        errors.add(CommonError(field))
    }
}