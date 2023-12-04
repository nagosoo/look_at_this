package com.eunji.lookatthis.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.eunji.lookatthis.MainActivity
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var isIdNotEmpty = false
    private var isPwNotEmpty = false
    private var isPwRecheckNotEmpty = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.let { mainActivity ->
            mainActivity.setAppBarTitle(getString(R.string.text_sign_up))
        }
        setEdittextListener()
    }

    private fun setSignUpButton() {
        binding.buttonSignUp.isEnabled = isIdNotEmpty && isPwNotEmpty && isPwRecheckNotEmpty
    }

    private fun setEdittextListener() {
        binding.etId.addTextChangedListener { text ->
            isIdNotEmpty = text?.isNotEmpty() ?: false
            setSignUpButton()
        }
        binding.etPw.addTextChangedListener { text ->
            isPwNotEmpty = text?.isNotEmpty() ?: false
            setSignUpButton()
        }
        binding.etPwRecheck.addTextChangedListener { text ->
            isPwRecheckNotEmpty = text?.isNotEmpty() ?: false
            setSignUpButton()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}