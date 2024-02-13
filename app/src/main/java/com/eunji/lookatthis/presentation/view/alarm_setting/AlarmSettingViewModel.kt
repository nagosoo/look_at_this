package com.eunji.lookatthis.presentation.view.alarm_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.data.model.AlarmModel
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.usecase.alarm.GetAlarmSettingUseCase
import com.eunji.lookatthis.domain.usecase.alarm.PostAlarmSettingUseCase
import com.eunji.lookatthis.presentation.model.AlarmType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    private val getAlarmSettingUseCase: GetAlarmSettingUseCase,
    private val postAlarmSettingUseCase: PostAlarmSettingUseCase,
) : ViewModel() {

    private val _checkedAlarmType: MutableLiveData<AlarmType> = MutableLiveData()
    val checkedAlarmType: LiveData<AlarmType> = _checkedAlarmType
    private val _uiState: MutableStateFlow<UiState<AlarmModel?>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AlarmModel?>> = _uiState
    private val _resultState: MutableStateFlow<UiState<AlarmModel?>> =
        MutableStateFlow(UiState.Loading)
    val resultState: StateFlow<UiState<AlarmModel?>> = _resultState

    init {
        viewModelScope.launch {
            getAlarmSettingUseCase()
                .stateIn(
                    initialValue = UiState.Loading,
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(
                        stopTimeoutMillis = 5000
                    )
                )
                .collect { uiState ->
                    _uiState.value = uiState
                }
        }
    }

    suspend fun postAlarmSetting(alarmModel: AlarmModel) {
        postAlarmSettingUseCase(alarmModel)
            .stateIn(
                initialValue = UiState.Loading,
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5000
                )
            )
            .collect { uiState ->
                _resultState.value = uiState
            }
    }

    fun setCheckedItem(item: AlarmType) {
        _checkedAlarmType.value = item
    }

}