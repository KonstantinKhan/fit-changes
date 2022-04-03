package ru.fit_chages.backend.product.service

import ru.fit_changes.openapi.models.BaseMessage

class DataNotAllowedException(message: String, request: BaseMessage) : Throwable("$message: $request")