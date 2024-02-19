package com.eunji.lookatthis.domain.model

data class Link(
    val linkId: Int,
    val linkUrl: String,
    val linkMemo: String?,
    val linkThumbnail: String?,
    val linkCreatedAt: String,
    var isRead: Boolean,
    var isBookmarked: Boolean,
)