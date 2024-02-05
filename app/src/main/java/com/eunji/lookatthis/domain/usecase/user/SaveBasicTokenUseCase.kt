package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.data.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveBasicTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        basicToken: String
    ) = userRepository.saveBasicToken(basicToken)
}