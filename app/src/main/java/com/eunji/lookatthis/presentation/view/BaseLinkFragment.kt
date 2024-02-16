package com.eunji.lookatthis.presentation.view

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.LayoutEmptyBinding
import com.eunji.lookatthis.databinding.LayoutLoadingBinding
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.LinkModel
import com.eunji.lookatthis.presentation.adapter.LinkAdapter
import com.eunji.lookatthis.presentation.adapter.LinkLoadStateAdapter
import com.eunji.lookatthis.presentation.decoration.LinkRecyclerViewItemDecoration
import com.eunji.lookatthis.presentation.util.DialogUtil
import com.eunji.lookatthis.presentation.util.DisplayUnitUtil
import com.eunji.lookatthis.presentation.util.UrlOpenUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseLinkViewModel : ViewModel() {
    abstract fun read(linkId: Int): Flow<UiState<LinkModel?>>
    abstract fun bookmark(linkId: Int): Flow<UiState<LinkModel?>>
}

abstract class BaseLinkFragment : Fragment() {
    fun setRecyclerView(
        adapter: LinkAdapter,
        recyclerView: RecyclerView,
        layoutLoading: LayoutLoadingBinding,
        layoutEmpty: LayoutEmptyBinding
    ) {
        val paddingBottom = DisplayUnitUtil.dpToPx(20f, requireContext())
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        recyclerView.addItemDecoration(LinkRecyclerViewItemDecoration(paddingBottom))

        adapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isFirstLoading = adapter.itemCount < 1 && loadState.refresh is LoadState.Loading
            layoutLoading.root.isVisible = isFirstLoading
            val isEmpty =
                adapter.itemCount < 1 && loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached
            layoutEmpty.root.isVisible = isEmpty
            val isError: LoadState.Error? =
                loadState.append as? LoadState.Error ?: loadState.refresh as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            isError?.let {
                DialogUtil.showErrorDialog(
                    parentFragmentManager,
                    getString(R.string.text_normal_error)
                )
            }
        }

        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LinkLoadStateAdapter()
        )
    }

    fun read(
        link: LinkModel,
        viewModel: BaseLinkViewModel,
        setReadView: () -> Unit,
    ) {
        if (!link.isRead) {
            setReadView()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.read(link.linkId)
                    .collect { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {}

                            is UiState.Success -> {
                                UrlOpenUtil.openUrl(
                                    uiState.value!!.linkUrl,
                                    requireContext(),
                                    parentFragmentManager
                                )
                            }

                            is UiState.Error -> {
                                DialogUtil.showErrorDialog(
                                    parentFragmentManager,
                                    uiState.errorMessage
                                )
                            }
                        }
                    }
            }
        } else {
            UrlOpenUtil.openUrl(link.linkUrl, requireContext(), parentFragmentManager)
        }
    }

    fun bookmark(
        link: LinkModel,
        viewModel: BaseLinkViewModel,
        toggleBookmarkView: () -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookmark(link.linkId)
                .collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {}

                        is UiState.Success -> {
                            toggleBookmarkView()
                        }

                        is UiState.Error -> {
                            DialogUtil.showErrorDialog(
                                parentFragmentManager,
                                uiState.errorMessage
                            )
                        }
                    }
                }
        }
    }
}