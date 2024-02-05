package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.data.model.PostUserAccountReqModel
import com.eunji.lookatthis.data.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
     operator fun invoke(
        memberId: String,
        memberPassword: String,
    ) = userRepository.postSignIn(
        PostUserAccountReqModel(
            memberPassword = memberPassword,
            memberId = memberId,
        )
    )
}