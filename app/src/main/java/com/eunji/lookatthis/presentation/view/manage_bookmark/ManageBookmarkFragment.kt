package com.eunji.lookatthis.presentation.view.manage_bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentManageBookmarkBinding
import com.eunji.lookatthis.presentation.view.BaseLinkFragment
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageBookmarkFragment : BaseLinkFragment() {

    private var _binding: FragmentManageBookmarkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = viewModels<ManageBookmarkViewModel>().value
        recyclerView = binding.recyclerView
        layoutLoading = binding.layoutLoading
        layoutEmpty = binding.layoutEmpty
        toggleBookmarkView = { _, _ ->
            val layoutManagerState = binding.recyclerView.layoutManager?.onSaveInstanceState()
            adapter.refresh()
            binding.recyclerView.layoutManager?.onRestoreInstanceState(layoutManagerState)
        }
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.text_manage_bookmark))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bundle = Bundle()
        bundle.putIntegerArrayList(
            LinkFragment.bookmarkIds,
            (viewModel as ManageBookmarkViewModel).bookmarkOffIds as ArrayList<Int>
        )
        bundle.putIntegerArrayList(
            LinkFragment.readIds,
            (viewModel as ManageBookmarkViewModel).readIds as ArrayList<Int>
        )
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