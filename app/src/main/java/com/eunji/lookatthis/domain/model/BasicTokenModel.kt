package com.eunji.lookatthis.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BasicTokenModel(
    @SerialName("memberBasicToken")
    val basicToken : String
)