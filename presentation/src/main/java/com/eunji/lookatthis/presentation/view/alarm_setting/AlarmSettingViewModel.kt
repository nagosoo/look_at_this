package com.eunji.lookatthis.presentation.view.alarm_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Alarm
import com.eunji.lookatthis.domain.status.AlarmType
import com.eunji.lookatthis.domain.usecase.alarm.GetAlarmSettingUseCase
import com.eunji.lookatthis.domain.usecase.alarm.PostAlarmSettingUseCase
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
    private val _uiState: MutableStateFlow<UiState<Alarm?>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Alarm?>> = _uiState
    private val _saveState: MutableStateFlow<UiState<Alarm?>> =
        MutableStateFlow(UiState.None)
    val saveState: StateFlow<UiState<Alarm?>> = _saveState

    fun getAlarmSetting() {
        viewModelScope.launch {
            getAlarmSettingUseCase()
                .stateIn(
                    initialValue = UiState.Loading,
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(
                        stopTimeoutMillis = 5000
                    )
                ).collect { uiState ->
                    _uiState.value = uiState
                }
        }

    }

    fun postAlarmSetting(alarm: Alarm) {
        viewModelScope.launch {
            postAlarmSettingUseCase(alarm)
                .stateIn(
                    initialValue = UiState.None,
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(
                        stopTimeoutMillis = 5000
                    )
                ).collect { saveState ->
                    _saveState.value = saveState
                }
        }
    }

    fun resetUiState() {
        _uiState.value = UiState.None
    }

    fun resetSaveState() {
        _saveState.value = UiState.None
    }

    fun setCheckedItem(item: AlarmType) {
        _checkedAlarmType.value = item
    }

}