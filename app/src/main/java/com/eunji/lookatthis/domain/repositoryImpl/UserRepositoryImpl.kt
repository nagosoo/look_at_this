package com.eunji.lookatthis.domain.repositoryImpl

import com.eunji.lookatthis.data.datasource.local.UserDataSourceLocal
import com.eunji.lookatthis.data.datasource.remote.UserDataSourceRemote
import com.eunji.lookatthis.data.model.PostUserReqModel
import com.eunji.lookatthis.data.model.ResponseModel
import com.eunji.lookatthis.data.repository.UserRepository
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataSourceLocal: UserDataSourceLocal,
    private val userDataSourceRemote: UserDataSourceRemote,
) : UserRepository {
    override fun saveBase64UserAccount(userName: String, password: String) {
        userDataSourceLocal.saveBase64UserAccount(userName, password)
    }

    override fun getBase64UserAccount(): String {
        return userDataSourceLocal.getBase64UserAccount()
    }

    override  fun postUserAccount(postUserReqModel: PostUserReqModel): Flow<UiState<Int?>> =
        safeApiCall { userDataSourceRemote.postUser() }
}