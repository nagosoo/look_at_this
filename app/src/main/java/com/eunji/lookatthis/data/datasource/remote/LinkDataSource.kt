package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.retrofit.services.LinkService
import com.eunji.lookatthis.domain.model.BookmarkReqModel
import com.eunji.lookatthis.domain.model.LinkModel
import com.eunji.lookatthis.domain.model.PaginationModel
import com.eunji.lookatthis.domain.model.PostLinkReqModel
import com.eunji.lookatthis.domain.model.ReadReqModel
import com.eunji.lookatthis.domain.model.ResponseModel
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkDataSource @Inject constructor(
    private val linkService: LinkService
) {

    suspend fun postLink(linkReqModel: PostLinkReqModel): Response<ResponseModel<LinkModel?>> =
        linkService.postLink(linkReqModel)

    suspend fun postLinkBookmark(bookmarkReqModel: BookmarkReqModel): Response<ResponseModel<LinkModel?>> =
        linkService.postLinkBookmark(bookmarkReqModel)

    suspend fun postLinkRead(readReqModel: ReadReqModel): Response<ResponseModel<LinkModel?>> =
        linkService.postLinkRead(readReqModel)

    suspend fun getLinks(
        pageSize: Int? = null,
        cursorId: Int? = null
    ): Response<PaginationModel<LinkModel>> = linkService.getLinks(pageSize, cursorId)

    suspend fun getBookmarkLinks(
        pageSize: Int? = null,
        cursorId: Int? = null
    ): Response<PaginationModel<LinkModel>> = linkService.getBookmarkLinks(pageSize, cursorId)

}