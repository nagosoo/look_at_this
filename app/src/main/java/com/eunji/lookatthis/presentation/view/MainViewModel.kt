package com.eunji.lookatthis.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.data.model.FcmTokenModel
import com.eunji.lookatthis.domain.usecase.alarm.PostFcmTokenUseCase
import com.eunji.lookatthis.presentation.model.AlarmType
import com.eunji.lookatthis.presentation.util.ApiRetry.retry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fcmTokenUseCase: PostFcmTokenUseCase,
) : ViewModel() {

    private val _alarmType: MutableLiveData<AlarmType?> = MutableLiveData()
    val alarmType: LiveData<AlarmType?> = _alarmType

    fun setAlarmType(alarmType: AlarmType) {
        _alarmType.value = alarmType
    }

    fun postFcmToken(fcmToken: String) {
        viewModelScope.launch {
            retry(2) {
                fcmTokenUseCase(FcmTokenModel(fcmToken)).collect()
            }
        }
    }

}