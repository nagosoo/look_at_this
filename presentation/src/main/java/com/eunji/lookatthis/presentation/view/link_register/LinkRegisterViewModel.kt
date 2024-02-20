package com.eunji.lookatthis.presentation.view.link_register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.domain.usecase.links.PostLinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkRegisterViewModel @Inject constructor(
    private val postLinkUseCase: PostLinkUseCase
) : ViewModel() {

    private val _url: MutableLiveData<String?> = MutableLiveData()
    val url: LiveData<String?> = _url
    private val _memo: MutableLiveData<String?> = MutableLiveData()
    val memo: LiveData<String?> = _memo
    private val _uiState: MutableStateFlow<UiState<Link?>> = MutableStateFlow(UiState.None)
    val uiState: StateFlow<UiState<Link?>> = _uiState

    fun setUrl(url: String) {
        _url.value = url
    }

    fun setMemo(memo: String) {
        _memo.value = memo
    }

    fun postLink() {
        _uiState.value= UiState.Loading
        viewModelScope.launch {
            postLinkUseCase(
                _url.value!!, _memo.value
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

}