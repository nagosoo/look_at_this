package com.eunji.lookatthis.data.retrofit.services

import com.eunji.lookatthis.data.model.BasicTokenDto
import com.eunji.lookatthis.data.model.CommonResponse
import com.eunji.lookatthis.data.model.PostUserAccountReqParam
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/member")
    suspend fun postSingUp(
        @Body body: PostUserAccountReqParam
    ): Response<CommonResponse<BasicTokenDto?>>

    @POST("/member/login")
    suspend fun postSignIn(
        @Body body: PostUserAccountReqParam
    ): Response<CommonResponse<BasicTokenDto?>>

}