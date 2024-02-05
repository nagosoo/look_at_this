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
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.presentation.view.CommonDialog
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()
    private var id: String? = null
    private var password: String? = null
    private var reCheckPassword: String? = null
    private var isPasswordSame: Boolean = false

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
            signUp()
        }
    }

    private fun signUp() {
        if (!isPasswordSame) {
            showErrorDialog(getString(R.string.text_not_same_password))
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postAccountResultFlow(
                    id = id!!,
                    password = password!!,
                ).collect {
                    render(it)
                }
            }
        }
    }

    private fun showErrorDialog(title: String) {
        CommonDialog(
            title = title,
            drawableResId = R.drawable.error,
        ).show(childFragmentManager, CommonDialog.TAG)
    }

    private fun render(uiState: UiState<String?>) {
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                uiState.value?.let { token ->
                    viewModel.saveBasicToken(token, ::goToMain)
                }
            }

            is UiState.Error -> {
                showErrorDialog(uiState.errorMessage)
            }
        }
    }

    private fun goToMain() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .commit()
    }

    private fun setSignUpButton() {
        binding.buttonSignUp.isEnabled =
            !id.isNullOrBlank() && !password.isNullOrBlank() && !reCheckPassword.isNullOrBlank()
    }

    private fun setOnEdittextListener() {
        binding.etId.addTextChangedListener { text ->
            id = text.toString()
            setSignUpButton()
        }
        binding.etPw.addTextChangedListener { text ->
            password = text.toString()
            isPasswordSame = password == reCheckPassword
            setSignUpButton()
        }
        binding.etPwRecheck.addTextChangedListener { text ->
            reCheckPassword = text.toString()
            isPasswordSame = password == reCheckPassword
            setSignUpButton()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
