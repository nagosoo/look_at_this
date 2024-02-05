package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.data.model.PostUserAccountReqModel
import com.eunji.lookatthis.data.model.TokenModel
import com.eunji.lookatthis.data.repository.UserRepository
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostSignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
     operator fun invoke(
        memberId: String,
        memberPassword: String,
    ) : Flow<UiState<TokenModel?>> = userRepository.postSignUp(
        PostUserAccountReqModel(
            memberPassword = memberPassword,
            memberId = memberId,
        )
    )
}