package com.eunji.lookatthis.presentation.view.manage_bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eunji.lookatthis.data.model.BookmarkReqModel
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.ReadReqModel
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.usecase.links.GetBookmarkLinkUseCase
import com.eunji.lookatthis.domain.usecase.links.PostLinkBookmarkUseCase
import com.eunji.lookatthis.domain.usecase.links.PostLinkReadUseCase
import com.eunji.lookatthis.presentation.view.BaseLinkViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageBookmarkViewModel @Inject constructor(
    private val getBookmarkLinkUseCase: GetBookmarkLinkUseCase,
    private val postLinkReadUseCase: PostLinkReadUseCase,
    private val postLinkBookmarkUseCase: PostLinkBookmarkUseCase,
) : BaseLinkViewModel() {

    private val _links: MutableStateFlow<PagingData<LinkModel>> =
        MutableStateFlow(PagingData.empty())
    val links: StateFlow<PagingData<LinkModel>> = _links

    private val _bookmarkOffIds: MutableList<Int> = mutableListOf()
    val bookmarkOffIds: List<Int> = _bookmarkOffIds

    private val _readIds: MutableList<Int> = mutableListOf()
    val readIds: List<Int> = _readIds

    init {
        getBookmarkLinks()
    }

    private fun addBookmarkOffLinkId(id: Int) {
        _bookmarkOffIds.add(id)
    }

    private fun addReadLinkId(id: Int) {
        _readIds.add(id)
    }

    private fun getBookmarkLinks() {
        viewModelScope.launch {
            getBookmarkLinkUseCase()
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

    override fun read(linkId: Int): Flow<UiState<LinkModel?>> {
        addReadLinkId(linkId)
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

    override fun bookmark(linkId: Int): Flow<UiState<LinkModel?>> {
        addBookmarkOffLinkId(linkId)
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