package com.eunji.lookatthis.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostUserAccountReqModel(
    @SerialName("memberEmail")
    val memberId: String,
    val memberPassword: String
)