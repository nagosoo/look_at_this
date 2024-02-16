package com.eunji.lookatthis.data.retrofit.services

import com.eunji.lookatthis.domain.model.BookmarkReqModel
import com.eunji.lookatthis.domain.model.LinkModel
import com.eunji.lookatthis.domain.model.PaginationModel
import com.eunji.lookatthis.domain.model.PostLinkReqModel
import com.eunji.lookatthis.domain.model.ReadReqModel
import com.eunji.lookatthis.domain.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LinkService {

    @POST("/link")
    suspend fun postLink(
        @Body body: PostLinkReqModel
    ): Response<ResponseModel<LinkModel?>>

    @GET("/link")
    suspend fun getLinks(
        @Query("pageSize") pageSize: Int? = null,
        @Query("cursorId") cursorId: Int? = null,
    ): Response<PaginationModel<LinkModel>>

    @GET("/link/bookmark")
    suspend fun getBookmarkLinks(
        @Query("pageSize") pageSize: Int? = null,
        @Query("cursorId") cursorId: Int? = null,
    ): Response<PaginationModel<LinkModel>>

    @POST("/link/bookmark")
    suspend fun postLinkBookmark(
        @Body body: BookmarkReqModel
    ): Response<ResponseModel<LinkModel?>>

    @POST("/link/read")
    suspend fun postLinkRead(
        @Body body: ReadReqModel
    ): Response<ResponseModel<LinkModel?>>

}