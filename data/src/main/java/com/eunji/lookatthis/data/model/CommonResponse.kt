package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CommonResponse<T>(
    val isSuccess: Boolean,
    val response: T?,
    val error: String?
)