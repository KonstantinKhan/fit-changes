package ru.fit_changes.backend.product.service

import ru.fit_changes.openapi.models.BaseMessage

class DataNotAllowedException(message: String, request: BaseMessage) : Throwable("$message: $request")