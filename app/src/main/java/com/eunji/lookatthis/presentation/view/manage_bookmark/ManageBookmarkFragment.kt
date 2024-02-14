package com.eunji.lookatthis.presentation.view.manage_bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.eunji.lookatthis.R
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.databinding.FragmentManageBookmarkBinding
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.presentation.adapter.LinkAdapter
import com.eunji.lookatthis.presentation.adapter.LinkLoadStateAdapter
import com.eunji.lookatthis.presentation.decoration.LinkRecyclerViewItemDecoration
import com.eunji.lookatthis.presentation.util.DialogUtil
import com.eunji.lookatthis.presentation.util.DisplayUnitUtil
import com.eunji.lookatthis.presentation.util.UrlOpenUtil
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageBookmarkFragment : Fragment() {

    private var _binding: FragmentManageBookmarkBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ManageBookmarkViewModel by viewModels()
    private val adapter by lazy { LinkAdapter(::read, ::bookmark) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.text_manage_bookmark))
        setRecyclerView()
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

    private fun read(link: LinkModel, position: Int) {
        if (!link.isRead) {
            setReadView(position)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.read(link.linkId)
                    .collect { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                            }

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

    private fun setReadView(position: Int) {
        adapter.snapshot()[position]?.isRead = true
        adapter.notifyItemChanged(position)
    }

    private fun bookmark(link: LinkModel, position: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookmark(link.linkId)
                .collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                        }

                        is UiState.Success -> {
                            adapter.notifyItemRemoved(position)
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

    private fun setRecyclerView() {
        val paddingBottom = DisplayUnitUtil.dpToPx(20f, requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(LinkRecyclerViewItemDecoration(paddingBottom))
        adapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isFirstLoading = adapter.itemCount < 1 && loadState.refresh is LoadState.Loading
            binding.layoutLoading.root.isVisible = isFirstLoading
            val isEmpty =
                adapter.itemCount < 1 && loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached
            binding.layoutEmpty.root.isVisible = isEmpty
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
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LinkLoadStateAdapter()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bundle = Bundle()
        bundle.putIntegerArrayList(
            LinkFragment.bookmarkIds,
            viewModel.bookmarkOffIds as ArrayList<Int>
        )
        bundle.putIntegerArrayList(LinkFragment.readIds, viewModel.readIds as ArrayList<Int>)
        setFragmentResult(
            LinkFragment.resultFromManageBookmarkView,
            bundle
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}