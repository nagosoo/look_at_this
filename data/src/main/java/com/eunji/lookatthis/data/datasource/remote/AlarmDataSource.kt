package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.retrofit.services.AlarmService
import com.eunji.lookatthis.data.model.AlarmDto
import com.eunji.lookatthis.data.model.CommonResponse
import com.eunji.lookatthis.domain.model.FcmTokenParam
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmDataSource @Inject constructor(
    private val alarmService: AlarmService
) {

    suspend fun getAlarmSetting(): Response<CommonResponse<AlarmDto?>> =
        alarmService.getAlarmSetting()

    suspend fun postAlarmSetting(
        alarmDto: AlarmDto
    ): Response<CommonResponse<AlarmDto?>> =
        alarmService.postAlarmSetting(body = alarmDto)

    suspend fun postFcmToken(
        fcmTokenParam: FcmTokenParam
    ): Response<CommonResponse<Boolean?>> =
        alarmService.postFcmToken(body = fcmTokenParam)

}
