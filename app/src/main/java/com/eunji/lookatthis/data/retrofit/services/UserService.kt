package com.eunji.lookatthis.data.retrofit.services

import com.eunji.lookatthis.data.model.PostUserReqModel
import com.eunji.lookatthis.data.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/member")
    suspend fun postUser(
        @Body body: PostUserReqModel
    ): Response<ResponseModel<Int?>>

}