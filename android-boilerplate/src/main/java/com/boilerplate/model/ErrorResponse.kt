package com.boilerplate.model

data class ErrorResponse<T>(
    val message: String?,
    val errors: T?
)
