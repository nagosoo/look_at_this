package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBasicTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<String?> = userRepository.getBasicToken()
}