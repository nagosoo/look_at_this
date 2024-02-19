package com.eunji.lookatthis.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostUserAccountReqParam(
    @SerialName("memberEmail")
    val memberId: String,
    val memberPassword: String
)