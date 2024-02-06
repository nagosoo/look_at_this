package com.eunji.lookatthis.data.retrofit.services

import com.eunji.lookatthis.data.model.AlarmModel
import com.eunji.lookatthis.data.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlarmService {

    @GET("/member/alarm")
    suspend fun getAlarmSetting(): Response<ResponseModel<AlarmModel?>>

    @POST("/member/alarm")
    suspend fun postAlarmSetting(
        @Body body: AlarmModel
    ): Response<ResponseModel<AlarmModel?>>

}