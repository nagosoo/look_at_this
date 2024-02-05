package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.model.PostUserAccountReqModel
import com.eunji.lookatthis.data.model.ResponseModel
import com.eunji.lookatthis.data.model.TokenModel
import com.eunji.lookatthis.data.retrofit.services.UserService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSourceRemote @Inject constructor(
    private val userService: UserService
) {
    suspend fun postSignUp(
        postUserAccountReqModel: PostUserAccountReqModel
    ): Response<ResponseModel<TokenModel?>> = userService.postSingUp(
        body = postUserAccountReqModel
    )

    suspend fun postSignIn(
        postUserAccountReqModel: PostUserAccountReqModel
    ): Response<ResponseModel<TokenModel?>> = userService.postSignIn(
        body = postUserAccountReqModel
    )
}