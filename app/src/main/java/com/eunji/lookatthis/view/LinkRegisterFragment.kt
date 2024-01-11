package com.eunji.lookatthis.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.eunji.lookatthis.MainActivity
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentLinkRegisterBinding

class LinkRegisterFragment : Fragment() {

    private var _binding: FragmentLinkRegisterBinding? = null
    private val binding get() = _binding!!
    private var isLinkNotEmpty = false

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
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.link_register))
        setOnEditTextListener()
    }

    private fun setOnEditTextListener() {
        binding.etLink.addTextChangedListener {
            isLinkNotEmpty = it?.isNotEmpty() ?: false
            setLinkRegisterButton()
        }

        binding.etMemo.addTextChangedListener {
            val memoSize = it?.length ?: 0
            setMemoSize(memoSize)
        }
    }

    private fun setMemoSize(memoSize: Int) {
        val color =
            if (memoSize == 100) requireContext().getColor(R.color.red)
            else requireContext().getColor(R.color.grey_dark)
        binding.tvCount.apply {
            text = "$memoSize${requireContext().getString(R.string.max_memo_size)}"
            setTextColor(color)
        }
    }

    private fun setLinkRegisterButton() {
        binding.btnRegister.isEnabled = isLinkNotEmpty
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
