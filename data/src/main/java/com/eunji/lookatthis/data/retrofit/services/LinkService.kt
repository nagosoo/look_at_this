package com.eunji.lookatthis.data.retrofit.services

import com.eunji.lookatthis.data.model.BookmarkReqParam
import com.eunji.lookatthis.data.model.CommonPagination
import com.eunji.lookatthis.data.model.CommonResponse
import com.eunji.lookatthis.data.model.LinkDto
import com.eunji.lookatthis.data.model.PostLinkReqParam
import com.eunji.lookatthis.data.model.ReadReqParam

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LinkService {

    @POST("/link")
    suspend fun postLink(
        @Body body: PostLinkReqParam
    ): Response<CommonResponse<LinkDto?>>

    @GET("/link")
    suspend fun getLinks(
        @Query("pageSize") pageSize: Int? = null,
        @Query("cursorId") cursorId: Int? = null,
    ): Response<CommonPagination<LinkDto>>

    @GET("/link/bookmark")
    suspend fun getBookmarkLinks(
        @Query("pageSize") pageSize: Int? = null,
        @Query("cursorId") cursorId: Int? = null,
    ): Response<CommonPagination<LinkDto>>

    @POST("/link/bookmark")
    suspend fun postLinkBookmark(
        @Body body: BookmarkReqParam
    ): Response<CommonResponse<LinkDto?>>

    @POST("/link/read")
    suspend fun postLinkRead(
        @Body body: ReadReqParam
    ): Response<CommonResponse<LinkDto?>>

}