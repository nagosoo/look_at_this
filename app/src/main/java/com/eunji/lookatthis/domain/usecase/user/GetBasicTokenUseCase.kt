package com.eunji.lookatthis.domain.usecase.user

import com.eunji.lookatthis.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBasicTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<String?> = userRepository.getBasicToken()
}