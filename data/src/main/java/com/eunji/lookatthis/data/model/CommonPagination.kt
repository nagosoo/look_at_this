package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CommonPagination<T>(
    val values: List<T>,
    val hasNext: Boolean,
    val nextCursor: Int?
)