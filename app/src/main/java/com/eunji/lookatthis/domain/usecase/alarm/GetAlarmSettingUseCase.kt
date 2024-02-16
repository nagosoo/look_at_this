package com.eunji.lookatthis.domain.usecase.alarm

import com.eunji.lookatthis.data.repository.AlarmRepository
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.AlarmModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlarmSettingUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(): Flow<UiState<AlarmModel?>> = alarmRepository.getAlarmSetting()

}