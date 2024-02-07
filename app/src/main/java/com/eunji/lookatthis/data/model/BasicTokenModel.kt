package com.eunji.lookatthis.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BasicTokenModel(
    @SerialName("memberBasicToken")
    val basicToken : String
)