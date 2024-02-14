package com.eunji.lookatthis.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LinkModel(
    val linkId: Int,
    val linkUrl: String,
    val linkMemo: String?,
    val linkThumbnail: String?,
    val linkCreatedAt: String,
    @SerialName("linkIsRead")
    var isRead: Boolean,
    @SerialName("linkIsBookmark")
    var isBookmarked: Boolean,
) : Parcelable