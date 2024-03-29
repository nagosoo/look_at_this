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
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.BasicToken
import com.eunji.lookatthis.presentation.R
import com.eunji.lookatthis.presentation.databinding.FragmentSignUpBinding
import com.eunji.lookatthis.presentation.util.DialogUtil.showErrorDialog
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()
    private var id: String? = null

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
        setObserver()
        init()
        subscribeUiState()
    }

    private fun init() {
        viewModel.id.value?.let { id ->
            binding.etId.setText(id)
        }

        viewModel.password.value?.let { password ->
            binding.etPw.setText(password)
        }

        viewModel.reCheckPassword.value?.let { reCheckPassword ->
            binding.etPwRecheck.setText(reCheckPassword)
        }
    }

    private fun setObserver() {
        viewModel.id.observe(viewLifecycleOwner) {
            setSignUpButton()
        }
        viewModel.password.observe(viewLifecycleOwner) {
            setSignUpButton()
        }
        viewModel.reCheckPassword.observe(viewLifecycleOwner) {
            setSignUpButton()
        }
    }

    private fun setOnClickListener() {
        binding.buttonSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        if (viewModel.isPasswordSame.value != true) {
            showErrorDialog(parentFragmentManager, getString(R.string.text_not_same_password))
            return
        }
        viewModel.signUp(
            id = viewModel.id.value!!,
            password = viewModel.password.value!!
        )
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

    private fun render(uiState: UiState<BasicToken?>) {
        when (uiState) {
            is UiState.None -> {}
            is UiState.Loading -> {}
            is UiState.Success -> {
                uiState.value?.let { value ->
                    viewModel.saveBasicToken(value.basicToken, ::goToMain)
                }
            }

            is UiState.Error -> {
                showErrorDialog(parentFragmentManager, uiState.errorMessage) {
                    viewModel.resetUiState()
                }
            }
        }
    }

    private fun goToMain() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LinkFragment())
            .commit()
    }

    private fun setSignUpButton() {
        binding.buttonSignUp.isEnabled =
            !viewModel.id.value.isNullOrBlank() && !viewModel.password.value.isNullOrBlank() && !viewModel.reCheckPassword.value.isNullOrBlank()
    }

    private fun setOnEdittextListener() {
        binding.etId.addTextChangedListener { text ->
            viewModel.setId(text.toString())
        }
        binding.etPw.addTextChangedListener { text ->
            viewModel.setPassword(text.toString())
        }
        binding.etPwRecheck.addTextChangedListener { text ->
            viewModel.setReCheckPassword(text.toString())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
