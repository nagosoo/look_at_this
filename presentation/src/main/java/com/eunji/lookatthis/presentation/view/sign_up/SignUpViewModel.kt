package com.eunji.lookatthis.presentation.view.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.BasicToken
import com.eunji.lookatthis.domain.usecase.user.PostSignUpUseCase
import com.eunji.lookatthis.domain.usecase.user.SaveBasicTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val postSignUpUseCase: PostSignUpUseCase,
    private val userBasicTokenUseCase: SaveBasicTokenUseCase
) : ViewModel() {

    private val _id: MutableLiveData<String?> = MutableLiveData()
    val id: LiveData<String?> = _id
    private val _password: MutableLiveData<String?> = MutableLiveData()
    val password: LiveData<String?> = _password
    private val _reCheckPassword: MutableLiveData<String?> = MutableLiveData()
    val reCheckPassword: LiveData<String?> = _reCheckPassword
    private val _isPasswordSame: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPasswordSame: LiveData<Boolean> = _isPasswordSame
    private val _uiState: MutableStateFlow<UiState<BasicToken?>> =
        MutableStateFlow(UiState.None)
    val uiState: StateFlow<UiState<BasicToken?>> = _uiState

    fun setId(id: String) {
        _id.value = id
    }

    fun setPassword(password: String) {
        _password.value = password
        _isPasswordSame.value = _password.value == _reCheckPassword.value
    }

    fun setReCheckPassword(reCheckPassword: String) {
        _reCheckPassword.value = reCheckPassword
        _isPasswordSame.value = _password.value == _reCheckPassword.value
    }

    fun signUp(
        id: String,
        password: String,
    ) {
        viewModelScope.launch {
            postSignUpUseCase(
                memberId = id,
                memberPassword = password,
            )
                .stateIn(
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
            userBasicTokenUseCase(token)
        }.invokeOnCompletion { throwable ->
            if (throwable == null) onSuccessListener()
        }
    }
}