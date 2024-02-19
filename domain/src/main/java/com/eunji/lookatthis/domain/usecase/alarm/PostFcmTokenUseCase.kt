package com.eunji.lookatthis.domain.usecase.alarm

import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostFcmTokenUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
) {

    operator fun invoke(fcmToken: String): Flow<UiState<Boolean?>> =
        alarmRepository.postFcmToken(fcmToken)

}