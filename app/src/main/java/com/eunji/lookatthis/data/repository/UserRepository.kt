package com.eunji.lookatthis.data.repository

import com.eunji.lookatthis.data.model.PostUserAccountReqModel
import com.eunji.lookatthis.data.model.TokenModel
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveBasicToken(basicToken: String)
    fun getBasicToken(): Flow<String?>
    fun postSignUp(postUserAccountReqModel: PostUserAccountReqModel): Flow<UiState<TokenModel?>>
    fun postSignIn(postUserAccountReqModel: PostUserAccountReqModel): Flow<UiState<TokenModel?>>

}