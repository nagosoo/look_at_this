package com.eunji.lookatthis.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenModel(
    @SerialName("memberBasicToken")
    val basicToken : String
)