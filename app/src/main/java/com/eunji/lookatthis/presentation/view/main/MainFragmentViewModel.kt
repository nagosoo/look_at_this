package com.eunji.lookatthis.presentation.view.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eunji.lookatthis.data.model.BookmarkReqModel
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.ReadReqModel
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.usecase.links.GetLinkUseCase
import com.eunji.lookatthis.domain.usecase.links.PostLinkBookmarkUseCase
import com.eunji.lookatthis.domain.usecase.links.PostLinkReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getLinkUseCase: GetLinkUseCase,
    private val postLinkReadUseCase: PostLinkReadUseCase,
    private val postLinkBookmarkUseCase: PostLinkBookmarkUseCase,
) : ViewModel() {

    fun getLinks(): StateFlow<PagingData<LinkModel>> {
        return getLinkUseCase()
            .cachedIn(viewModelScope)
            .stateIn(
                initialValue = PagingData.empty(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5000
                )
            )
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

}