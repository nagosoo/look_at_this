package com.eunji.lookatthis.domain.usecase.alarm

import com.eunji.lookatthis.data.model.AlarmModel
import com.eunji.lookatthis.data.repository.AlarmRepository
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostAlarmSettingUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(alarmModel: AlarmModel): Flow<UiState<AlarmModel?>> =
        alarmRepository.postAlarmSetting(alarmModel)

}