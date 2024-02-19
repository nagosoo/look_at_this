package com.eunji.lookatthis.presentation.view.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.BasicToken
import com.eunji.lookatthis.domain.usecase.user.PostSignInUseCase
import com.eunji.lookatthis.domain.usecase.user.SaveBasicTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val postSignInUseCase: PostSignInUseCase,
    private val saveBasicTokenUseCase: SaveBasicTokenUseCase,
) : ViewModel() {

    private val _id: MutableLiveData<String?> = MutableLiveData()
    val id: LiveData<String?> = _id
    private val _password: MutableLiveData<String?> = MutableLiveData()
    val password: LiveData<String?> = _password
    private val _uiState: MutableStateFlow<UiState<BasicToken?>> =
        MutableStateFlow(UiState.None)
    val uiState: StateFlow<UiState<BasicToken?>> = _uiState

    fun setId(id: String) {
        _id.value = id
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun signIn(
        id: String,
        password: String
    ) {
        viewModelScope.launch {
            postSignInUseCase(
                memberId = id,
                memberPassword = password
            ).stateIn(
                initialValue = UiState.None,
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5000
                )
            ).collect { uiState ->
                _uiState.value = uiState
            }
        }
    }

    fun resetUiState() {
        _uiState.value = UiState.None
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