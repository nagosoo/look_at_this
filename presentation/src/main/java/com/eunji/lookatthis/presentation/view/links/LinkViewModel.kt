package com.eunji.lookatthis.presentation.view.links

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.domain.usecase.alarm.PostFcmTokenUseCase
import com.eunji.lookatthis.domain.usecase.links.GetLinkUseCase
import com.eunji.lookatthis.domain.usecase.links.PostLinkBookmarkUseCase
import com.eunji.lookatthis.domain.usecase.links.PostLinkReadUseCase
import com.eunji.lookatthis.presentation.view.BaseLinkViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkViewModel @Inject constructor(
    private val getLinkUseCase: GetLinkUseCase,
    private val postLinkReadUseCase: PostLinkReadUseCase,
    private val postLinkBookmarkUseCase: PostLinkBookmarkUseCase,
    private val postFcmTokenUseCase: PostFcmTokenUseCase,
) : BaseLinkViewModel() {

    private val _links: MutableStateFlow<PagingData<Link>> =
        MutableStateFlow(PagingData.empty())
    override val links: StateFlow<PagingData<Link>> = _links

    private val _readUiState: MutableStateFlow<UiState<Link?>> =
        MutableStateFlow(UiState.None)
    override val readUiState: StateFlow<UiState<Link?>> = _readUiState

    private val _bookmarkUiState: MutableStateFlow<UiState<Link?>> =
        MutableStateFlow(UiState.None)
    override val bookmarkUiState: StateFlow<UiState<Link?>> = _bookmarkUiState

    init {
        getAllLinks()
    }

    private fun getAllLinks() {
        viewModelScope.launch {
            getLinkUseCase()
                .cachedIn(viewModelScope)
                .stateIn(
                    initialValue = PagingData.empty(),
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(
                        stopTimeoutMillis = 5000
                    )
                ).collect { pagingData ->
                    _links.value = pagingData
                }
        }
    }

    fun postFcmToken(fcmToken: String) {
        viewModelScope.launch {
            postFcmTokenUseCase(fcmToken).collect()
        }
    }

    override fun read(linkId: Int) {
        viewModelScope.launch {
            postLinkReadUseCase(
                linkId = linkId
            ).stateIn(
                initialValue = UiState.None,
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5000
                )
            ).collect { readState ->
                _readUiState.value = readState
            }
        }
    }

    override fun bookmark(linkId: Int) {
        viewModelScope.launch {
            postLinkBookmarkUseCase(
                linkId = linkId
            ).stateIn(
                initialValue = UiState.None,
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5000
                )
            ).collect { bookmarkState ->
                _bookmarkUiState.value = bookmarkState
            }
        }
    }

    override fun resetReadState() {
        _readUiState.value = UiState.None
    }

    override fun resetBookmarkState() {
        _bookmarkUiState.value = UiState.None
    }

}