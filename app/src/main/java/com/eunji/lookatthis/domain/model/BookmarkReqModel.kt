package com.eunji.lookatthis.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BookmarkReqModel (
    val linkId : Int
)