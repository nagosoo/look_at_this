package com.eunji.lookatthis.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eunji.lookatthis.presentation.model.AlarmType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _alarmType: MutableLiveData<AlarmType?> = MutableLiveData()
    val alarmType: LiveData<AlarmType?> = _alarmType

    fun setAlarmType(alarmType: AlarmType) {
        _alarmType.value = alarmType
    }

}