package com.eunji.lookatthis.domain.repositoryImpl

import com.eunji.lookatthis.data.datasource.remote.AlarmDataSource
import com.eunji.lookatthis.data.model.AlarmModel
import com.eunji.lookatthis.data.repository.AlarmRepository
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val alarmDataSource: AlarmDataSource
) : AlarmRepository {

    override fun getAlarmSetting(): Flow<UiState<AlarmModel?>> =
        safeApiCall { alarmDataSource.getAlarmSetting() }

    override fun postAlarmSetting(alarmModel: AlarmModel): Flow<UiState<AlarmModel?>> =
        safeApiCall { alarmDataSource.postAlarmSetting(alarmModel) }
}