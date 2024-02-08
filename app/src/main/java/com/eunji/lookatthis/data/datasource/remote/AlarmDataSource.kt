package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.model.AlarmModel
import com.eunji.lookatthis.data.model.FcmTokenModel
import com.eunji.lookatthis.data.model.ResponseModel
import com.eunji.lookatthis.data.retrofit.services.AlarmService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmDataSource @Inject constructor(
    private val alarmService: AlarmService
) {

    suspend fun getAlarmSetting(): Response<ResponseModel<AlarmModel?>> =
        alarmService.getAlarmSetting()

    suspend fun postAlarmSetting(
        alarmModel: AlarmModel
    ): Response<ResponseModel<AlarmModel?>> =
        alarmService.postAlarmSetting(body = alarmModel)

    suspend fun postFcmToken(
        fcmTokenModel: FcmTokenModel
    ): Response<ResponseModel<Boolean?>> =
        alarmService.postFcmToken(body = fcmTokenModel)

}