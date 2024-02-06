package com.eunji.lookatthis.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AlarmModel(
    val keepReceiveAlarms: Boolean,
    val alarmTime: String,
)