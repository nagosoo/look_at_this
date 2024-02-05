package com.eunji.lookatthis.presentation.view.alarm_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eunji.lookatthis.presentation.model.AlarmType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(

) : ViewModel() {

    private val _checkedItem: MutableLiveData<AlarmType> = MutableLiveData()
    val checkedItem: LiveData<AlarmType> = _checkedItem

    fun setCheckedItem(item: AlarmType) {
        _checkedItem.value = item
    }

}