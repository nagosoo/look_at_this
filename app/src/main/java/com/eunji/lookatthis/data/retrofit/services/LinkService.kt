package com.eunji.lookatthis.data.retrofit.services

import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.PaginationModel
import com.eunji.lookatthis.data.model.PostLinkReqModel
import com.eunji.lookatthis.data.model.ResponseModel
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

}