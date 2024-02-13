package com.eunji.lookatthis.presentation.view.links

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eunji.lookatthis.data.model.BookmarkReqModel
import com.eunji.lookatthis.data.model.FcmTokenModel
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.ReadReqModel
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.usecase.alarm.PostFcmTokenUseCase
import com.eunji.lookatthis.domain.usecase.links.GetLinkUseCase
import com.eunji.lookatthis.domain.usecase.links.PostLinkBookmarkUseCase
import com.eunji.lookatthis.domain.usecase.links.PostLinkReadUseCase
import com.eunji.lookatthis.presentation.util.ApiRetry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
) : ViewModel() {

    private val _links: MutableStateFlow<PagingData<LinkModel>> =
        MutableStateFlow(PagingData.empty())
    val links: StateFlow<PagingData<LinkModel>> = _links

    init {
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

    fun read(linkId: Int): Flow<UiState<LinkModel?>> {
        return postLinkReadUseCase(
            ReadReqModel(
                linkId = linkId
            )
        ).stateIn(
            initialValue = UiState.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5000
            )
        )
    }

    fun bookmark(linkId: Int): Flow<UiState<LinkModel?>> {
        return postLinkBookmarkUseCase(
            BookmarkReqModel(
                linkId = linkId
            )
        ).stateIn(
            initialValue = UiState.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5000
            )
        )
    }

    fun postFcmToken(fcmToken: String) {
        viewModelScope.launch {
            ApiRetry.retry(2) {
                postFcmTokenUseCase(FcmTokenModel(fcmToken)).collect()
            }
        }
    }

}