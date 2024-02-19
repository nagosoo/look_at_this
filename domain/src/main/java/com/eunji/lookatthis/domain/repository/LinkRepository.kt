package com.eunji.lookatthis.domain.repository

import androidx.paging.PagingData
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkRepository {

    fun postLink(linkUrl: String, linkMemo: String?): Flow<UiState<Link?>>
    fun postLinkBookmark(linkId: Int): Flow<UiState<Link?>>
    fun postLinkRead(linkId: Int): Flow<UiState<Link?>>
    fun getLinks(): Flow<PagingData<Link>>
    fun getBookmarkLinks(): Flow<PagingData<Link>>

}