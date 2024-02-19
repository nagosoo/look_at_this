package com.eunji.lookatthis.domain.repository

import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarmSetting(): Flow<UiState<Alarm?>>
    fun postAlarmSetting(alarm: Alarm): Flow<UiState<Alarm?>>
    fun postFcmToken(fcmToken: String): Flow<UiState<Boolean?>>

}