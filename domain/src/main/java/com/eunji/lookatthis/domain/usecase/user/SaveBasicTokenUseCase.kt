package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.domain.repository.UserRepository
import javax.inject.Inject

class SaveBasicTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        basicToken: String
    ) = userRepository.saveBasicToken(basicToken)
}