package com.eunji.lookatthis.presentation.view.manage_bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentManageBookmarkBinding
import com.eunji.lookatthis.domain.model.LinkModel
import com.eunji.lookatthis.presentation.adapter.LinkAdapter
import com.eunji.lookatthis.presentation.view.BaseLinkFragment
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageBookmarkFragment : BaseLinkFragment() {

    private var _binding: FragmentManageBookmarkBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ManageBookmarkViewModel by viewModels()
    private val adapter by lazy { LinkAdapter(::readCallback, ::bookmarkCallback) }


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
        setRecyclerView(
            adapter = adapter,
            recyclerView = binding.recyclerView,
            layoutLoading = binding.layoutLoading,
            layoutEmpty = binding.layoutEmpty
        )
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

    private fun readCallback(link: LinkModel, position: Int) {
        read(
            link = link,
            viewModel = viewModel
        ) {
            setReadView(position)
        }
    }

    private fun bookmarkCallback(link: LinkModel, position: Int) {
        bookmark(
            link = link,
            viewModel = viewModel
        ) {
            val layoutManagerState = binding.recyclerView.layoutManager?.onSaveInstanceState()
            adapter.refresh()
            binding.recyclerView.layoutManager?.onRestoreInstanceState(layoutManagerState)
        }
    }

    private fun setReadView(position: Int) {
        adapter.snapshot()[position]?.isRead = true
        adapter.notifyItemChanged(position)
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