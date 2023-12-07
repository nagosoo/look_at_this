package com.eunji.lookatthis.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentCommonDialogBinding


class CommonDialog : DialogFragment() {

    private var _binding: FragmentCommonDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CommonDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommonDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()
        setContent()
    }

    private fun setImage() {
        binding.iv.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.error
            )
        )
    }

    private fun setContent() {
        binding.tvContent.text = "이미 존재하는 아이디입니다."
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "CommonDialog"
    }

}