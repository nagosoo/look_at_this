package com.eunji.lookatthis.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel<T>(
    val isSuccess: Boolean,
    val response: T?,
    val error: String?
)