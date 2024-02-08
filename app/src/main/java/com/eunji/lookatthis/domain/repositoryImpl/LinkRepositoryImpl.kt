package com.eunji.lookatthis.domain.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eunji.lookatthis.data.datasource.remote.LinkDataSource
import com.eunji.lookatthis.data.model.BookmarkReqModel
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.PostLinkReqModel
import com.eunji.lookatthis.data.model.ReadReqModel
import com.eunji.lookatthis.data.repository.LinkRepository
import com.eunji.lookatthis.domain.LinkPagingSource
import com.eunji.lookatthis.domain.LinkPagingSource.Companion.NETWORK_PAGE_SIZE
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRepositoryImpl @Inject constructor(
    private val linkDataSource: LinkDataSource,
) : LinkRepository {

    override fun postLink(linkReqModel: PostLinkReqModel): Flow<UiState<LinkModel?>> =
        safeApiCall { linkDataSource.postLink(linkReqModel) }

    override fun postLinkBookmark(bookmarkReqModel: BookmarkReqModel): Flow<UiState<LinkModel?>> =
        safeApiCall { linkDataSource.postLinkBookmark(bookmarkReqModel) }

    override fun postLinkRead(readReqModel: ReadReqModel): Flow<UiState<LinkModel?>> =
        safeApiCall { linkDataSource.postLinkRead(readReqModel) }

    override fun getLinks(): Flow<PagingData<LinkModel>> {
        val pageConfig = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
        )

        return Pager(
            config = pageConfig,
            pagingSourceFactory = { LinkPagingSource(linkDataSource) }
        ).flow
    }
}