package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PostLinkReqParam(
    val linkUrl: String,
    val linkMemo: String?
)