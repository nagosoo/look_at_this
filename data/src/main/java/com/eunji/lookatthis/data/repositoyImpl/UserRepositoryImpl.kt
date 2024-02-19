package com.eunji.lookatthis.data.repositoyImpl

import com.eunji.lookatthis.data.datasource.local.UserDataSourceLocal
import com.eunji.lookatthis.data.datasource.remote.UserDataSourceRemote
import com.eunji.lookatthis.data.model.PostUserAccountReqParam
import com.eunji.lookatthis.data.safeApiCall
import com.eunji.lookatthis.data.util.ConvertUiStateToDomain.toDomainUiState
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.BasicToken
import com.eunji.lookatthis.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataSourceLocal: UserDataSourceLocal,
    private val userDataSourceRemote: UserDataSourceRemote,
) : UserRepository {
    override suspend fun saveBasicToken(basicToken: String) {
        userDataSourceLocal.saveBasicToken(basicToken)
    }

    override fun getBasicToken(): Flow<String?> {
        return userDataSourceLocal.getBasicToken()
    }

    override fun postSignUp(id: String, password: String): Flow<UiState<BasicToken?>> =
        safeApiCall {
            userDataSourceRemote.postSignUp(
                PostUserAccountReqParam(
                    id,
                    password
                )
            )
        }.map { uiState ->
            uiState.toDomainUiState()
        }

    override fun postSignIn(id: String, password: String): Flow<UiState<BasicToken?>> =
        safeApiCall {
            userDataSourceRemote.postSignIn(
                PostUserAccountReqParam(
                    id,
                    password
                )
            )
        }.map { uiState ->
            uiState.toDomainUiState()
        }
}