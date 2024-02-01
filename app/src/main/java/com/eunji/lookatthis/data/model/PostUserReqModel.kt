package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PostUserReqModel(
    val memberEmail: String,
    val memberPassword: String
)