package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.model.PostUserReqModel
import com.eunji.lookatthis.data.model.ResponseModel
import com.eunji.lookatthis.data.retrofit.services.UserService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSourceRemote @Inject constructor(
    private val userService: UserService
) {
    suspend fun postUser(): Response<ResponseModel<Int?>> = userService.postUser(
        body = PostUserReqModel(
            memberEmail = "nagosoo2",
            memberPassword = "1234"
        )
    )
}