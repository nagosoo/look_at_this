package com.eunji.lookatthis.data.repositoyImpl

import com.eunji.lookatthis.data.datasource.remote.AlarmDataSource
import com.eunji.lookatthis.data.mapper.AlarmMapper.toDataModel
import com.eunji.lookatthis.data.safeApiCall
import com.eunji.lookatthis.data.util.ApiRetry
import com.eunji.lookatthis.data.util.ConvertUiStateToDomain.toDomainUiState
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Alarm
import com.eunji.lookatthis.domain.model.FcmTokenParam
import com.eunji.lookatthis.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val alarmDataSource: AlarmDataSource
) : AlarmRepository {

    override fun getAlarmSetting(): Flow<UiState<Alarm?>> =
        safeApiCall { alarmDataSource.getAlarmSetting() }.map { uiState ->
            uiState.toDomainUiState()
        }

    override fun postAlarmSetting(alarm: Alarm): Flow<UiState<Alarm?>> =
        safeApiCall { alarmDataSource.postAlarmSetting(alarm.toDataModel()) }.map { uiState ->
            uiState.toDomainUiState()
        }

    override fun postFcmToken(fcmToken: String): Flow<UiState<Boolean?>> =
        safeApiCall {
            ApiRetry.retry(2) {
                alarmDataSource.postFcmToken(FcmTokenParam(fcmToken))
            }
        }
}