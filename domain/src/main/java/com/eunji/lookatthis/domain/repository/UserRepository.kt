package com.eunji.lookatthis.domain.repository

import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.BasicToken
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveBasicToken(basicToken: String)
    fun getBasicToken(): Flow<String?>
    fun postSignUp(id: String, password: String): Flow<UiState<BasicToken?>>
    fun postSignIn(id: String, password: String): Flow<UiState<BasicToken?>>

}