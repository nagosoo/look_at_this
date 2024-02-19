package com.eunji.lookatthis.domain.model

import com.eunji.lookatthis.domain.status.AlarmType

data class Alarm(
    val keepReceiveAlarms: Boolean,
    val alarmTime: String,
) {
    companion object {

        fun getAlarmModelFromAlarmType(alarmType: AlarmType): Alarm {
            return Alarm(
                keepReceiveAlarms = alarmType == AlarmType.EVERY_TIME,
                alarmTime = alarmType.time ?: ""
            )
        }

        fun getAlarmTypeFromAlarmModel(alarm: Alarm): AlarmType {
            if (alarm.keepReceiveAlarms) return AlarmType.EVERY_TIME
            else {
                AlarmType.values().forEach { alarmType ->
                    if (alarm.alarmTime == alarmType.time) {
                        return alarmType
                    }
                }
            }
            throw Exception("해당되는 알림 타입이 없습니다.")
        }
    }
}