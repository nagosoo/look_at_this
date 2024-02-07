package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PostLinkReqModel(
    val linkUrl: String,
    val linkMemo: String?
)