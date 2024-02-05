package com.eunji.lookatthis.presentation.view.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.data.model.TokenModel
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.usecase.user.PostSignInUseCase
import com.eunji.lookatthis.domain.usecase.user.SaveBasicTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: PostSignInUseCase,
    private val saveBasicTokenUseCase: SaveBasicTokenUseCase,
) : ViewModel() {

    fun signIn(
        id: String,
        password: String
    ): Flow<UiState<TokenModel?>> {
        return signInUseCase(
            memberId = id,
            memberPassword = password
        ).stateIn(
            initialValue = UiState.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5000
            )
        )
    }

    fun saveBasicToken(token: String, onSuccessListener: () -> Unit) {
        viewModelScope.launch {
            saveBasicTokenUseCase(token)
        }.invokeOnCompletion { throwable ->
            if (throwable == null) {
                onSuccessListener()
            }
        }

    }
}