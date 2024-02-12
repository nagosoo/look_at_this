package com.eunji.lookatthis.presentation.view.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.eunji.lookatthis.R
import com.eunji.lookatthis.data.model.BasicTokenModel
import com.eunji.lookatthis.databinding.FragmentSignInBinding
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.presentation.util.DialogUtil.showErrorDialog
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()

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
        init()
        setOnEdittextListener()
        setOnClickListener()
    }

    private fun init() {
        viewModel.id.value?.let { id ->
            binding.etId.setText(id)
        }

        viewModel.password.value?.let { password ->
            binding.etPw.setText(password)
        }
    }

    private fun setOnClickListener() {
        binding.buttonSignUp.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signIn(id = viewModel.id.value!!, password = viewModel.password.value!!)
                .collect {
                    render(it)
                }
        }
    }

    private fun render(uiState: UiState<BasicTokenModel?>) {
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                uiState.value?.let { value ->
                    viewModel.saveBasicToken(value.basicToken, ::goToMain)
                }
            }

            is UiState.Error -> {
                showErrorDialog(parentFragmentManager, uiState.errorMessage)
            }
        }
    }

    private fun goToMain() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LinkFragment())
            .commit()
    }

    private fun setSignInButton() {
        binding.buttonSignUp.isEnabled =
            !viewModel.id.value.isNullOrBlank() && !viewModel.password.value.isNullOrBlank()
    }

    private fun setOnEdittextListener() {
        binding.etId.addTextChangedListener { text ->
            viewModel.setId(text.toString())
            setSignInButton()
        }
        binding.etPw.addTextChangedListener { text ->
            viewModel.setPassword(text.toString())
            setSignInButton()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
