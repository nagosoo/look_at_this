package com.eunji.lookatthis.presentation.view

import androidx.lifecycle.ViewModel
import com.eunji.lookatthis.presentation.model.AlarmType

class MainViewModel : ViewModel() {

    private var _alarmType: AlarmType? = null
    val alarmType
        get() = _alarmType

    fun setAlarmType(alarmType: AlarmType) {
        _alarmType = alarmType
    }

}