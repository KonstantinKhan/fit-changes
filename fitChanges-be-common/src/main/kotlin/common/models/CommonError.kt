package common.models

data class CommonError(
    override var field: String = "",
) : IError