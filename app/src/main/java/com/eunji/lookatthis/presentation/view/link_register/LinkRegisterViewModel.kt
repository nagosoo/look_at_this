package com.eunji.lookatthis.presentation.view.link_register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.PostLinkReqModel
import com.eunji.lookatthis.domain.UiState
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
    private val _uiState: MutableStateFlow<UiState<LinkModel?>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<LinkModel?>> = _uiState

    fun setUrl(url: String) {
        _url.value = url
    }

    fun setMemo(memo: String) {
        _memo.value = memo
    }

    fun postLink() {
        viewModelScope.launch {
            postLinkUseCase(
                PostLinkReqModel(
                    _url.value!!, _memo.value
                )
            ).stateIn(
                initialValue = UiState.Loading,
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5000
                )
            ).collect { uiState ->
                _uiState.value = uiState
            }
        }
    }
}