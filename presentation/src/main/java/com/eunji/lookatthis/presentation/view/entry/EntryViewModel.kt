package com.eunji.lookatthis.presentation.view.entry

import androidx.lifecycle.ViewModel
import com.eunji.lookatthis.domain.usecase.user.GetBasicTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val userBasicTokenUseCase: GetBasicTokenUseCase
) : ViewModel() {

    suspend fun getBasicToken(): String? {
        return userBasicTokenUseCase().first()
    }

}