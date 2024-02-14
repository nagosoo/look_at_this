package com.eunji.lookatthis.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentCommonDialogBinding


class CommonDialog(
    private val title: String,
    private val drawableResId: Int,
    private val onPositiveBtnClickListener: (() -> Unit)? = null,
    private val positiveBtnText: String = "확인",
    private val showPositiveButton: Boolean = true
) : DialogFragment() {

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
        setPositiveButtonVisibility()
        setImage()
        setContent()
        setButton()
    }

    private fun setPositiveButtonVisibility() {
        binding.btnPositive.isVisible = showPositiveButton
    }

    private fun setImage() {
        binding.iv.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                drawableResId
            )
        )
    }

    private fun setContent() {
        binding.tvContent.text = title
    }

    private fun setButton() {
        binding.btnPositive.text = positiveBtnText
        binding.btnPositive.setOnClickListener {
            onPositiveBtnClickListener?.invoke()
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "CommonDialog"
    }
}
