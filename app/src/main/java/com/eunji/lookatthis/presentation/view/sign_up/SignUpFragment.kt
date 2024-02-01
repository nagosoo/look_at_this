package com.eunji.lookatthis.presentation.view.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentSignUpBinding
import com.eunji.lookatthis.presentation.MainActivity
import com.eunji.lookatthis.domain.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var isIdNotEmpty = false
    private var isPwNotEmpty = false
    private var isPwRecheckNotEmpty = false
    private val viewModel: SignUpViewModel by viewModels()

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
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.text_sign_up))
        setOnEdittextListener()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.buttonSignUp.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.postAccountResultFlow.collect {
                        render(it)
                    }
                }
            }
//            CommonDialog(
//                title = getString(R.string.text_already_exist_id),
//                drawableResId = R.drawable.error,
//            ).show(childFragmentManager, CommonDialog.TAG)
        }
    }

    private fun render(uiState: UiState<Int?>) {
        when (uiState) {
            is UiState.Loading -> println("this is my uiState : Loading")
            is UiState.Success -> println("this is my uiState : Success")
            is UiState.Error -> println("this is my uiState : Error")
        }
    }

    private fun setSignUpButton() {
        binding.buttonSignUp.isEnabled = isIdNotEmpty && isPwNotEmpty && isPwRecheckNotEmpty
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
