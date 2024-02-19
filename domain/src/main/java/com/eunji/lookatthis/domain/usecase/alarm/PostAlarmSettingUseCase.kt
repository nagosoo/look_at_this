package com.eunji.lookatthis.domain.usecase.alarm

import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Alarm
import com.eunji.lookatthis.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostAlarmSettingUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(alarm: Alarm): Flow<UiState<Alarm?>> =
        alarmRepository.postAlarmSetting(alarm)

}