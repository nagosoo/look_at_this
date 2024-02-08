package com.eunji.lookatthis.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkModel(
    val linkId: Int,
    val linkUrl: String,
    val linkMemo: String?,
    val linkThumbnail: String?,
    val linkCreatedAt: String,
    @SerialName("linkIsRead")
    val isRead: Boolean,
    @SerialName("linkIsBookmark")
    var isBookmarked: Boolean,
)