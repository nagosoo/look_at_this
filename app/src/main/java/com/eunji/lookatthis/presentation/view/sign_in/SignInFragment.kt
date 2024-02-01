package com.eunji.lookatthis.presentation.view.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.eunji.lookatthis.presentation.MainActivity
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentSignInBinding
import com.eunji.lookatthis.presentation.view.CommonDialog

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private var isIdNotEmpty = false
    private var isPwNotEmpty = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.text_sign_in))
        setOnEdittextListener()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.buttonSignUp.setOnClickListener {
            CommonDialog(
                title = getString(R.string.text_wrong_id_or_pw),
                drawableResId = R.drawable.error,
            ).show(childFragmentManager, CommonDialog.TAG)
        }
    }

    private fun setSignUpButton() {
        binding.buttonSignUp.isEnabled = isIdNotEmpty && isPwNotEmpty
    }

    private fun setOnEdittextListener() {
        binding.etId.addTextChangedListener { text ->
            isIdNotEmpty = text?.isNotEmpty() ?: false
            setSignUpButton()
        }
        binding.etPw.addTextChangedListener { text ->
            isPwNotEmpty = text?.isNotEmpty() ?: false
            setSignUpButton()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
