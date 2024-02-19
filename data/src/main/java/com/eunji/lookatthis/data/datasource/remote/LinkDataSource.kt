package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.model.BookmarkReqParam
import com.eunji.lookatthis.data.model.CommonPagination
import com.eunji.lookatthis.data.model.CommonResponse
import com.eunji.lookatthis.data.retrofit.services.LinkService
import com.eunji.lookatthis.data.model.LinkDto
import com.eunji.lookatthis.data.model.PostLinkReqParam
import com.eunji.lookatthis.data.model.ReadReqParam
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkDataSource @Inject constructor(
    private val linkService: LinkService
) {

    suspend fun postLink(linkReqModel: PostLinkReqParam): Response<CommonResponse<LinkDto?>> =
        linkService.postLink(linkReqModel)

    suspend fun postLinkBookmark(bookmarkReqParam: BookmarkReqParam): Response<CommonResponse<LinkDto?>> =
        linkService.postLinkBookmark(bookmarkReqParam)

    suspend fun postLinkRead(readReqParam: ReadReqParam): Response<CommonResponse<LinkDto?>> =
        linkService.postLinkRead(readReqParam)

    suspend fun getLinks(
        pageSize: Int? = null,
        cursorId: Int? = null
    ): Response<CommonPagination<LinkDto>> = linkService.getLinks(pageSize, cursorId)

    suspend fun getBookmarkLinks(
        pageSize: Int? = null,
        cursorId: Int? = null
    ): Response<CommonPagination<LinkDto>> = linkService.getBookmarkLinks(pageSize, cursorId)

}