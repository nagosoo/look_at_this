package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.retrofit.services.UserService
import com.eunji.lookatthis.data.model.BasicTokenDto
import com.eunji.lookatthis.data.model.CommonResponse
import com.eunji.lookatthis.data.model.PostUserAccountReqParam
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSourceRemote @Inject constructor(
    private val userService: UserService
) {
    suspend fun postSignUp(
        postUserAccountReqParam: PostUserAccountReqParam
    ): Response<CommonResponse<BasicTokenDto?>> = userService.postSingUp(
        body = postUserAccountReqParam
    )

    suspend fun postSignIn(
        postUserAccountReqParam: PostUserAccountReqParam
    ): Response<CommonResponse<BasicTokenDto?>> = userService.postSignIn(
        body = postUserAccountReqParam
    )
}