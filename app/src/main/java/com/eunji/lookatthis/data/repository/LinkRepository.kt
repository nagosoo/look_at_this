package com.eunji.lookatthis.data.repository

import androidx.paging.PagingData
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.PostLinkReqModel
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow

interface LinkRepository {

    fun postLink(linkReqModel: PostLinkReqModel): Flow<UiState<LinkModel?>>
    fun getLinks(): Flow<PagingData<LinkModel>>

}