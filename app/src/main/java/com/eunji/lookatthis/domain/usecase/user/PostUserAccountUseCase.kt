package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.data.model.PostUserReqModel
import com.eunji.lookatthis.data.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostUserAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
     operator fun invoke(
        memberEmail: String,
        memberPassword: String,
    ) = userRepository.postUserAccount(
        PostUserReqModel(
            memberPassword = memberPassword,
            memberEmail = memberEmail,
        )
    )
}