package com.eunji.lookatthis.data.retrofit.services

import com.eunji.lookatthis.data.model.AlarmDto
import com.eunji.lookatthis.data.model.CommonResponse
import com.eunji.lookatthis.domain.model.FcmTokenParam
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlarmService {

    @GET("/member/alarm")
    suspend fun getAlarmSetting(): Response<CommonResponse<AlarmDto?>>

    @POST("/member/alarm")
    suspend fun postAlarmSetting(
        @Body body: AlarmDto
    ): Response<CommonResponse<AlarmDto?>>

    @POST("/member/fcm")
    suspend fun postFcmToken(
        @Body body: FcmTokenParam
    ): Response<CommonResponse<Boolean?>>

}