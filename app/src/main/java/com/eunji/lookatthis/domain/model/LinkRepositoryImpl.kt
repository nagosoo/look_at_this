package com.eunji.lookatthis.domain.model

import com.eunji.lookatthis.data.datasource.remote.LinkDataSource
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.PostLinkReqModel
import com.eunji.lookatthis.data.repository.LinkRepository
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRepositoryImpl @Inject constructor(
    private val linkDataSource: LinkDataSource
) : LinkRepository {

    override fun postLink(linkReqModel: PostLinkReqModel): Flow<UiState<LinkModel?>> =
        safeApiCall { linkDataSource.postLink(linkReqModel) }
}