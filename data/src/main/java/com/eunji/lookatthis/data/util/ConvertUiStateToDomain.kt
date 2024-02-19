package com.eunji.lookatthis.data.util

import com.eunji.lookatthis.data.model.DataModelInterface
import com.eunji.lookatthis.domain.UiState

object ConvertUiStateToDomain {

    fun <T> UiState<DataModelInterface<T>?>.toDomainUiState(): UiState<T?> {
        return when (this) {
            is UiState.None -> UiState.None
            is UiState.Success -> UiState.Success(this.value?.toDomainModel())
            is UiState.Loading -> UiState.Loading
            is UiState.Error -> UiState.Error(this.errorMessage)
        }
    }

}