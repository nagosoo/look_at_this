package com.eunji.lookatthis.data.retrofit.services

import com.eunji.lookatthis.domain.model.BasicTokenModel
import com.eunji.lookatthis.domain.model.PostUserAccountReqModel
import com.eunji.lookatthis.domain.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/member")
    suspend fun postSingUp(
        @Body body: PostUserAccountReqModel
    ): Response<ResponseModel<BasicTokenModel?>>

    @POST("/member/login")
    suspend fun postSignIn(
        @Body body: PostUserAccountReqModel
    ): Response<ResponseModel<BasicTokenModel?>>

}