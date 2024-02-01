package com.eunji.lookatthis.data.repository

import com.eunji.lookatthis.data.model.PostUserReqModel
import com.eunji.lookatthis.data.model.ResponseModel
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {

    fun saveBase64UserAccount(userName: String, password: String)
    fun getBase64UserAccount(): String
     fun postUserAccount(postUserReqModel: PostUserReqModel): Flow<UiState<Int?>>

}