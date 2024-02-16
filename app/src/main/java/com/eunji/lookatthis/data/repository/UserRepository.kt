package com.eunji.lookatthis.data.repository

import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.BasicTokenModel
import com.eunji.lookatthis.domain.model.PostUserAccountReqModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveBasicToken(basicToken: String)
    fun getBasicToken(): Flow<String?>
    fun postSignUp(postUserAccountReqModel: PostUserAccountReqModel): Flow<UiState<BasicTokenModel?>>
    fun postSignIn(postUserAccountReqModel: PostUserAccountReqModel): Flow<UiState<BasicTokenModel?>>

}