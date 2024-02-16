package com.eunji.lookatthis.domain

import com.eunji.lookatthis.domain.model.ErrorBodyModel
import com.eunji.lookatthis.domain.model.ResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response

fun <T> safeApiCall(apiCall: suspend () -> Response<ResponseModel<T>>): Flow<UiState<T?>> = flow {
    try {
        val result = apiCall()
        if (result.isSuccessful) {
            result.body()?.let {
                emit(UiState.Success(result.body()!!.response))
            }
        } else {
            val json = Json { ignoreUnknownKeys = true }
            val errorBody = result.errorBody()?.string() ?: ""
            val errorMessage = json.decodeFromString<ErrorBodyModel>(errorBody)
            emit(UiState.Error(errorMessage.error))
        }
    } catch (e: HttpException) {
        emit(UiState.Error("인터넷이 안돼ㅠ_ㅠ"))
    } catch (e: Exception) {
        emit(UiState.Error("잠시 후 시도해줘ㅠ_ㅠ"))
    }
}

