package com.eunji.lookatthis.data.repository

import androidx.paging.PagingData
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.BookmarkReqModel
import com.eunji.lookatthis.domain.model.LinkModel
import com.eunji.lookatthis.domain.model.PostLinkReqModel
import com.eunji.lookatthis.domain.model.ReadReqModel
import kotlinx.coroutines.flow.Flow

interface LinkRepository {

    fun postLink(linkReqModel: PostLinkReqModel): Flow<UiState<LinkModel?>>
    fun postLinkBookmark(bookmarkReqModel: BookmarkReqModel): Flow<UiState<LinkModel?>>
    fun postLinkRead(readReqModel: ReadReqModel): Flow<UiState<LinkModel?>>
    fun getLinks(): Flow<PagingData<LinkModel>>
    fun getBookmarkLinks(): Flow<PagingData<LinkModel>>

}