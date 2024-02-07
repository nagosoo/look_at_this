package com.eunji.lookatthis.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eunji.lookatthis.presentation.model.AlarmType

class MainViewModel : ViewModel() {

    private val _alarmType: MutableLiveData<AlarmType?> = MutableLiveData()
    val alarmType: LiveData<AlarmType?> = _alarmType

    fun setAlarmType(alarmType: AlarmType) {
        _alarmType.value = alarmType
    }

}