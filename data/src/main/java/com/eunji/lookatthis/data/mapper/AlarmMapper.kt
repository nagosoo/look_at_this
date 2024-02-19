package com.eunji.lookatthis.data.mapper

import com.eunji.lookatthis.domain.model.Alarm
import com.eunji.lookatthis.data.model.AlarmDto

object AlarmMapper {
    fun Alarm.toDataModel(): AlarmDto {
        return AlarmDto(
            keepReceiveAlarms = this.keepReceiveAlarms,
            alarmTime = this.alarmTime
        )
    }
}