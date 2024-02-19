package com.eunji.lookatthis.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.presentation.R
import com.eunji.lookatthis.presentation.adapter.LinkAdapter
import com.eunji.lookatthis.presentation.adapter.LinkLoadStateAdapter
import com.eunji.lookatthis.presentation.databinding.LayoutEmptyBinding
import com.eunji.lookatthis.presentation.databinding.LayoutLoadingBinding
import com.eunji.lookatthis.presentation.decoration.LinkRecyclerViewItemDecoration
import com.eunji.lookatthis.presentation.util.DialogUtil
import com.eunji.lookatthis.presentation.util.DisplayUnitUtil
import com.eunji.lookatthis.presentation.util.UrlOpenUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseLinkViewModel : ViewModel() {
    abstract val links: StateFlow<PagingData<Link>>
    abstract val readUiState: StateFlow<UiState<Link?>>
    abstract val bookmarkUiState: StateFlow<UiState<Link?>>

    abstract fun read(linkId: Int)
    abstract fun bookmark(linkId: Int)
    abstract fun resetBookmarkState()
    abstract fun resetReadState()
}

abstract class BaseLinkFragment : Fragment() {

    lateinit var viewModel: BaseLinkViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var layoutLoading: LayoutLoadingBinding
    lateinit var layoutEmpty: LayoutEmptyBinding
    lateinit var toggleBookmarkView: (Link, Int) -> Unit
    val adapter by lazy { LinkAdapter(::readCallback, ::bookmarkCallback) }
    private var bookmarkItem: Link? = null
    private var bookmarkItemPosition: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        subscribeReadState(viewModel.readUiState)
        subscribeBookmarkState(viewModel.bookmarkUiState)
        init()
    }

    private fun init() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.links
                    .collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
            }
        }
    }

    private fun setRecyclerView() {
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

    private fun readCallback(
        link: Link,
        position: Int,
    ) {
        if (!link.isRead) {
            setReadView(position)
            viewModel.read(link.linkId)
        } else {
            UrlOpenUtil.openUrl(link.linkUrl, requireContext(), parentFragmentManager)
        }
    }

    fun setReadView(position: Int) {
        adapter.snapshot()[position]?.isRead = true
        adapter.notifyItemChanged(position)
    }

    private fun bookmarkCallback(
        link: Link,
        position: Int,
    ) {
        bookmarkItem = link
        bookmarkItemPosition = position
        viewModel.bookmark(link.linkId)
    }

    private fun subscribeReadState(
        readState: Flow<UiState<Link?>>
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                readState.collect { readState ->
                    renderRead(readState)
                }
            }
        }
    }

    private fun subscribeBookmarkState(
        bookmarkState: Flow<UiState<Link?>>,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookmarkState.collect { bookmarkState ->
                    renderBookmark(bookmarkState)
                }
            }
        }
    }

    private fun renderRead(readState: UiState<Link?>) {
        when (readState) {
            is UiState.None -> {}
            is UiState.Loading -> {}

            is UiState.Success -> {
                viewModel.resetReadState()
                UrlOpenUtil.openUrl(
                    readState.value!!.linkUrl,
                    requireContext(),
                    parentFragmentManager
                )
            }

            is UiState.Error -> {
                DialogUtil.showErrorDialog(
                    parentFragmentManager,
                    readState.errorMessage
                ) {
                    viewModel.resetReadState()
                }
            }
        }
    }

    private fun renderBookmark(
        bookmarkState: UiState<Link?>,
    ) {
        when (bookmarkState) {
            is UiState.None -> {}
            is UiState.Loading -> {}

            is UiState.Success -> {
                viewModel.resetBookmarkState()
                toggleBookmarkView(bookmarkItem!!, bookmarkItemPosition!!)
            }

            is UiState.Error -> {
                DialogUtil.showErrorDialog(
                    parentFragmentManager,
                    bookmarkState.errorMessage
                ) {
                    viewModel.resetBookmarkState()
                }
            }
        }
    }
}