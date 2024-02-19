package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookmarkReqParam (
    val linkId : Int
)