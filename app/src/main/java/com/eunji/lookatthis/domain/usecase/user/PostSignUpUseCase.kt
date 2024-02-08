package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.data.model.PostUserAccountReqModel
import com.eunji.lookatthis.data.model.BasicTokenModel
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
    ) : Flow<UiState<BasicTokenModel?>> = userRepository.postSignUp(
        PostUserAccountReqModel(
            memberPassword = memberPassword,
            memberId = memberId,
        )
    )
}