package com.eunji.lookatthis.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReadReqModel (
    val linkId : Int
)