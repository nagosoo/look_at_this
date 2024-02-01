package com.eunji.lookatthis.presentation.view.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.usecase.user.PostUserAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userAccountUseCase: PostUserAccountUseCase
) : ViewModel() {

    val postAccountResultFlow: Flow<UiState<Int?>> = userAccountUseCase(
        memberPassword = "1234",
        memberEmail = "nagosoo2"
    )
        .stateIn(
            initialValue = UiState.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5000
            )
        )

}