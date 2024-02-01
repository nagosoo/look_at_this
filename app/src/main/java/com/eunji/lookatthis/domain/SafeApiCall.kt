package com.eunji.lookatthis.domain

import com.eunji.lookatthis.data.model.ResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response

fun <T> safeApiCall(apiCall: suspend () -> Response<ResponseModel<T>>): Flow<UiState<T?>> = flow {
    try {
        val result = apiCall()
        if (result.isSuccessful) {
            result.body()?.let {
                emit(UiState.Success(result.body()!!.response))
            }
        } else emit(UiState.Error("잠시 후 시도해주세요."))
    } catch (e: HttpException) {
        emit(UiState.Error("네트워크 에러 입니다."))
    } catch (e: Exception) {
        emit(UiState.Error("잠시 후 시도해주세요."))
    }
}