package com.eunji.lookatthis.domain.usecase.alarm

import com.eunji.lookatthis.domain.repository.AlarmRepository
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Alarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmSettingUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(): Flow<UiState<Alarm?>> = alarmRepository.getAlarmSetting()

}