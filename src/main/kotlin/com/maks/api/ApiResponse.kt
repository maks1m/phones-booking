package com.maks.api

data class ApiResponse(
    val data: Any? = null,
    val errors: List<String>? = null
)
