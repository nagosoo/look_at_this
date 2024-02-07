package com.eunji.lookatthis.domain.usecase.alarm

import com.eunji.lookatthis.data.model.FcmTokenModel
import com.eunji.lookatthis.data.repository.AlarmRepository
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostFcmTokenUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
) {

    operator fun invoke(fcmTokenModel: FcmTokenModel): Flow<UiState<Boolean?>> =
        alarmRepository.postFcmToken(fcmTokenModel)

}