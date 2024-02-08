package com.eunji.lookatthis.data.repository

import androidx.paging.PagingData
import com.eunji.lookatthis.data.model.BookmarkReqModel
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.PostLinkReqModel
import com.eunji.lookatthis.data.model.ReadReqModel
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow

interface LinkRepository {

    fun postLink(linkReqModel: PostLinkReqModel): Flow<UiState<LinkModel?>>
    fun postLinkBookmark(bookmarkReqModel: BookmarkReqModel): Flow<UiState<LinkModel?>>
    fun postLinkRead(readReqModel: ReadReqModel): Flow<UiState<LinkModel?>>
    fun getLinks(): Flow<PagingData<LinkModel>>

}