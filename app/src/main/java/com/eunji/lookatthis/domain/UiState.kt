package com.eunji.lookatthis.domain

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()

    data class Success<T>(
        val value: T
    ) : UiState<T>()

    data class Error(
        val errorMessage: String
    ) : UiState<Nothing>()
}