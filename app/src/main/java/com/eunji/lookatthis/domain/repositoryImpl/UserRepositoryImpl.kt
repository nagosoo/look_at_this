package com.eunji.lookatthis.domain.repositoryImpl

import com.eunji.lookatthis.data.datasource.local.UserDataSourceLocal
import com.eunji.lookatthis.data.datasource.remote.UserDataSourceRemote
import com.eunji.lookatthis.data.model.PostUserAccountReqModel
import com.eunji.lookatthis.data.repository.UserRepository
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.safeApiCall
import kotlinx.coroutines.flow.Flow
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

    override fun postSignUp(postUserAccountReqModel: PostUserAccountReqModel): Flow<UiState<String?>> =
        safeApiCall { userDataSourceRemote.postSignUp(postUserAccountReqModel) }

    override fun postSignIn(postUserAccountReqModel: PostUserAccountReqModel): Flow<UiState<String?>> =
        safeApiCall { userDataSourceRemote.postSignIn(postUserAccountReqModel) }
}