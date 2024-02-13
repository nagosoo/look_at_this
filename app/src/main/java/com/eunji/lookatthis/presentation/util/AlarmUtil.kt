package com.eunji.lookatthis.presentation.util

import com.eunji.lookatthis.data.model.AlarmModel
import com.eunji.lookatthis.presentation.model.AlarmType

object AlarmUtil {

    fun getAlarmModelFromAlarmType(alarmType: AlarmType): AlarmModel {
        return AlarmModel(
            keepReceiveAlarms = alarmType == AlarmType.EVERY_TIME,
            alarmTime = alarmType.time ?: ""
        )
    }

    fun getAlarmTypeFromAlarmModel(alarmModel: AlarmModel): AlarmType {
        if (alarmModel.keepReceiveAlarms) return AlarmType.EVERY_TIME
        else {
            AlarmType.values().forEach { alarmType ->
                if (alarmModel.alarmTime == alarmType.time) {
                    return alarmType
                }
            }
        }
        throw Exception("해당되는 알림 타입이 없습니다.")
    }

}