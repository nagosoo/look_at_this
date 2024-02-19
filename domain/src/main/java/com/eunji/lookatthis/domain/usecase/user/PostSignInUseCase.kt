package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.BasicToken
import com.eunji.lookatthis.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(
        memberId: String,
        memberPassword: String,
    ): Flow<UiState<BasicToken?>> = userRepository.postSignIn(
        memberId,
        memberPassword
    )
}