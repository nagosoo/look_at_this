package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.retrofit.services.UserService
import com.eunji.lookatthis.domain.model.BasicTokenModel
import com.eunji.lookatthis.domain.model.PostUserAccountReqModel
import com.eunji.lookatthis.domain.model.ResponseModel
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSourceRemote @Inject constructor(
    private val userService: UserService
) {
    suspend fun postSignUp(
        postUserAccountReqModel: PostUserAccountReqModel
    ): Response<ResponseModel<BasicTokenModel?>> = userService.postSingUp(
        body = postUserAccountReqModel
    )

    suspend fun postSignIn(
        postUserAccountReqModel: PostUserAccountReqModel
    ): Response<ResponseModel<BasicTokenModel?>> = userService.postSignIn(
        body = postUserAccountReqModel
    )
}