package com.eunji.lookatthis.data.model

import com.eunji.lookatthis.domain.model.Link
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkDto(
    val linkId: Int,
    val linkUrl: String,
    val linkMemo: String?,
    val linkThumbnail: String?,
    val linkCreatedAt: String,
    @SerialName("linkIsRead")
    var isRead: Boolean,
    @SerialName("linkIsBookmark")
    var isBookmarked: Boolean,
) : DataModelInterface<Link> {
    override fun toDomainModel(): Link {
        return Link(
            linkId = this.linkId,
            linkUrl = this.linkUrl,
            linkMemo = this.linkMemo,
            linkThumbnail = this.linkThumbnail,
            linkCreatedAt = this.linkCreatedAt,
            isRead = this.isRead,
            isBookmarked = this.isBookmarked,
        )
    }
}