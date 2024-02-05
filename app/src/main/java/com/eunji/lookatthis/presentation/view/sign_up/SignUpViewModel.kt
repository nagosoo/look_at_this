package com.eunji.lookatthis.presentation.view.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.usecase.user.PostSignUpUseCase
import com.eunji.lookatthis.domain.usecase.user.SaveBasicTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userAccountUseCase: PostSignUpUseCase,
    private val userBasicTokenUseCase: SaveBasicTokenUseCase
) : ViewModel() {

//    private var _id: MutableLiveData<String?> = MutableLiveData()
//    val id: LiveData<String?> = _id
//    private var _password: MutableLiveData<String?> = MutableLiveData()
//    val password: LiveData<String?> = _password
//    private var _reCheckPassword: MutableLiveData<String?> = MutableLiveData()
//    val reCheckPassword: LiveData<String?> = _reCheckPassword
//
//    fun setId(id: String) {
//        _id.value = id
//    }
//
//    fun setPassword(password: String) {
//        _password.value = password
//    }
//
//    fun setReCheckPassword(reCheckPassword: String) {
//        _reCheckPassword.value = reCheckPassword
//    }

    fun postAccountResultFlow(
        id: String,
        password: String,
    ): Flow<UiState<String?>> = userAccountUseCase(
        memberId = id,
        memberPassword = password,
    )
        .stateIn(
            initialValue = UiState.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5000
            )
        )

    fun saveBasicToken(token: String, onSuccessListener: () -> Unit) {
        viewModelScope.launch {
            userBasicTokenUseCase(token)
        }.invokeOnCompletion {throwable->
            if (throwable == null) onSuccessListener()
        }
    }
}