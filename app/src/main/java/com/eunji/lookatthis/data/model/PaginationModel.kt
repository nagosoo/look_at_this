package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginationModel<T>(
    val values: List<T>,
    val hasNext: Boolean,
    val nextCursor: Int?
)