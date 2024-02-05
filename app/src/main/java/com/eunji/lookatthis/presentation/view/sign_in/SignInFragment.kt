package com.eunji.lookatthis.presentation.view.sign_in

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
import com.eunji.lookatthis.data.model.TokenModel
import com.eunji.lookatthis.databinding.FragmentSignInBinding
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.presentation.view.CommonDialog
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()
    private var id: String? = null
    private var password: String? = null

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
            signIn()
        }
    }

    private fun signIn() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signIn(id = id!!, password = password!!).collect {
                    render(it)
                }
            }
        }
    }

    private fun render(uiState: UiState<TokenModel?>) {
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                uiState.value?.let { value ->
                    viewModel.saveBasicToken(value.basicToken, ::goToMain)
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

    private fun showErrorDialog(title: String) {
        CommonDialog(
            title = title,
            drawableResId = R.drawable.error,
        ).show(childFragmentManager, CommonDialog.TAG)
    }

    private fun setSignInButton() {
        binding.buttonSignUp.isEnabled = !id.isNullOrBlank() && !password.isNullOrBlank()
    }

    private fun setOnEdittextListener() {
        binding.etId.addTextChangedListener { text ->
            id = text.toString()
            setSignInButton()
        }
        binding.etPw.addTextChangedListener { text ->
            password = text.toString()
            setSignInButton()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
