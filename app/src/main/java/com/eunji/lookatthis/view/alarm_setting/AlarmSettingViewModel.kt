package com.eunji.lookatthis.view.alarm_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmSettingViewModel : ViewModel() {

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    fun setTime(hour: Int, minute: Int) {
        _time.value = "$hour:$minute"
    }
}