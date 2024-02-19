package com.eunji.lookatthis.data.model

import com.eunji.lookatthis.domain.model.Alarm
import kotlinx.serialization.Serializable

@Serializable
data class AlarmDto(
    val keepReceiveAlarms: Boolean,
    val alarmTime: String,
) : DataModelInterface<Alarm> {
    override fun toDomainModel(): Alarm {
        return Alarm(
            keepReceiveAlarms = this.keepReceiveAlarms,
            alarmTime = this.alarmTime
        )
    }
}

