package com.eunji.lookatthis.data.repositoyImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eunji.lookatthis.data.BookmarkLinkPagingSource
import com.eunji.lookatthis.data.BookmarkLinkPagingSource.Companion.BOOKMARK_LINK_NETWORK_PAGE_SIZE
import com.eunji.lookatthis.data.LinkPagingSource
import com.eunji.lookatthis.data.LinkPagingSource.Companion.ALL_LINK_NETWORK_PAGE_SIZE
import com.eunji.lookatthis.data.datasource.remote.LinkDataSource
import com.eunji.lookatthis.data.model.BookmarkReqParam
import com.eunji.lookatthis.data.model.PostLinkReqParam
import com.eunji.lookatthis.data.model.ReadReqParam
import com.eunji.lookatthis.data.safeApiCall
import com.eunji.lookatthis.data.util.ConvertUiStateToDomain.toDomainUiState
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.domain.repository.LinkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRepositoryImpl @Inject constructor(
    private val linkDataSource: LinkDataSource,
) : LinkRepository {

    override fun postLink(linkUrl: String, linkMemo: String?): Flow<UiState<Link?>> =
        safeApiCall {
            linkDataSource.postLink(
                PostLinkReqParam(
                    linkUrl,
                    linkMemo
                )
            )
        }.map { uiState ->
            uiState.toDomainUiState()
        }

    override fun postLinkBookmark(linkId: Int): Flow<UiState<Link?>> =
        safeApiCall { linkDataSource.postLinkBookmark(BookmarkReqParam(linkId)) }.map { uiState ->
            uiState.toDomainUiState()
        }

    override fun postLinkRead(linkId: Int): Flow<UiState<Link?>> =
        safeApiCall { linkDataSource.postLinkRead(ReadReqParam(linkId)) }.map { uiState ->
            uiState.toDomainUiState()
        }

    override fun getLinks(): Flow<PagingData<Link>> {
        val pageConfig = PagingConfig(
            pageSize = ALL_LINK_NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
        )

        return Pager(
            config = pageConfig,
            pagingSourceFactory = { LinkPagingSource(linkDataSource) }
        ).flow
    }

    override fun getBookmarkLinks(): Flow<PagingData<Link>> {
        val pageConfig = PagingConfig(
            pageSize = BOOKMARK_LINK_NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
        )

        return Pager(
            config = pageConfig,
            pagingSourceFactory = { BookmarkLinkPagingSource(linkDataSource) }
        ).flow
    }
}