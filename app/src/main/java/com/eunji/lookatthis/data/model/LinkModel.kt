package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LinkModel(
    val linkId: Int,
    val linkUrl: String,
    val linkMemo: String?,
    val linkThumbnail: String?,
    val linkCreatedAt: String,
    val linkIsRead: Boolean,
    val linkIsBookmark: Boolean,
)