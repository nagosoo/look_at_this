package com.eunji.lookatthis.data.repository

import com.eunji.lookatthis.data.model.AlarmModel
import com.eunji.lookatthis.data.model.FcmTokenModel
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarmSetting(): Flow<UiState<AlarmModel?>>
    fun postAlarmSetting(alarmModel: AlarmModel): Flow<UiState<AlarmModel?>>
    fun postFcmToken(fcmTokenModel: FcmTokenModel): Flow<UiState<Boolean?>>

}