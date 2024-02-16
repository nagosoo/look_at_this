package com.eunji.lookatthis.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PostLinkReqModel(
    val linkUrl: String,
    val linkMemo: String?
)