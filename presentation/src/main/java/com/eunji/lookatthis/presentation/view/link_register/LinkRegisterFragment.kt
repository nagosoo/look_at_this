package com.eunji.lookatthis.presentation.view.link_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.presentation.R
import com.eunji.lookatthis.presentation.databinding.FragmentLinkRegisterBinding
import com.eunji.lookatthis.presentation.util.ClipboardHelper
import com.eunji.lookatthis.presentation.util.DialogUtil
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LinkRegisterFragment : Fragment() {

    private var _binding: FragmentLinkRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LinkRegisterViewModel by viewModels()
    private var linkFromOtherApp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linkFromOtherApp = arguments?.getString(LinkFragment.linkFromOtherApp)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLinkRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.text_link_register))
        setOnEditTextListener()
        setOnClickListener()
        init()
        subscribeUiState()
    }

    private fun init() {
        linkFromOtherApp?.let { url ->
            binding.etLink.setText(url)
        }
        viewModel.url.value?.let { url ->
            binding.etLink.setText(url)
        }
        viewModel.memo.value?.let { memo ->
            binding.etMemo.setText(memo)
        }
    }

    private fun subscribeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    render(uiState)
                }
            }
        }
    }

    private fun setOnEditTextListener() {
        binding.etLink.addTextChangedListener { url ->
            viewModel.setUrl(url.toString())
            setLinkRegisterButton()
        }

        binding.etMemo.addTextChangedListener { memo ->
            viewModel.setMemo(memo.toString())
            val memoSize = memo?.length ?: 0
            setMemoSize(memoSize)
        }
    }

    private fun setMemoSize(memoSize: Int) {
        val color =
            if (memoSize == 100) requireContext().getColor(R.color.red)
            else requireContext().getColor(R.color.grey_dark)
        binding.tvCount.apply {
            text =
                "$memoSize${requireContext().getString(R.string.text_max_memo_size)}"
            setTextColor(color)
        }
    }

    private fun setLinkRegisterButton() {
        binding.btnRegister.isEnabled = viewModel.url.value?.isBlank() == false
    }

    private fun setOnClickListener() {
        binding.btnRegister.setOnClickListener {
            viewModel.postLink()
        }
        binding.btnPaste.setOnClickListener {
            paste()
        }
    }

    private fun render(uiState: UiState<Link?>) {
        when (uiState) {
            is UiState.None -> {}

            is UiState.Loading -> {
                DialogUtil.showLoadingDialog(
                    parentFragmentManager,
                    requireContext(),
                    requireContext().getString(R.string.text_registering)
                )
            }

            is UiState.Success -> {
                DialogUtil.closeDialog(parentFragmentManager)
                uiState.value?.let { value ->
                    DialogUtil.showSuccessDialog(
                        parentFragmentManager,
                        requireContext().getString(R.string.text_success_register_link)
                    ) {
                        setFragmentResult(
                            LinkFragment.shouldRefreshPagingKey,
                            bundleOf(LinkFragment.shouldRefreshPaging to true)
                        )
                        parentFragmentManager.popBackStack()
                    }
                }
            }

            is UiState.Error -> {
                DialogUtil.showErrorDialog(parentFragmentManager, uiState.errorMessage) {
                    viewModel.resetUiState()
                }
            }
        }
    }


    private fun paste() {
        val clipboardHelper = ClipboardHelper(requireContext())
        clipboardHelper.getTextFromClipBoard()?.let {
            binding.etLink.setText(it)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
