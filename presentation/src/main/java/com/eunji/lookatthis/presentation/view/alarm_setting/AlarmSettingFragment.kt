package com.eunji.lookatthis.presentation.view.alarm_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Alarm
import com.eunji.lookatthis.domain.model.Alarm.Companion.getAlarmModelFromAlarmType
import com.eunji.lookatthis.domain.model.Alarm.Companion.getAlarmTypeFromAlarmModel
import com.eunji.lookatthis.domain.status.AlarmType
import com.eunji.lookatthis.presentation.R
import com.eunji.lookatthis.presentation.databinding.FragmentAlarmSettingBinding
import com.eunji.lookatthis.presentation.util.DialogUtil.closeDialog
import com.eunji.lookatthis.presentation.util.DialogUtil.showErrorDialog
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.MainViewModel
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlarmSettingFragment : Fragment() {
    private var _binding: FragmentAlarmSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlarmSettingViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val alarmTypes = AlarmType.values().toList()
    private val checkBoxs: List<CheckBox?> by lazy {
        (binding.root.children.first() as ConstraintLayout).children.filter { view ->
            view.tag == "checkbox"
        }.map { view ->
            (view as? CustomAlarmSettingItem)?.checkBox
        }.toList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.text_alarm_setting_appbar))
        setOnClickListener()
        init()
        subscribeUiState()
        subscribeSaveState()
    }

    private fun subscribeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    renderUiState(uiState)
                }
            }
        }
    }

    private fun subscribeSaveState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveState.collect { saveState ->
                    renderSaveState(saveState)
                }
            }
        }
    }

    private fun init() {
        //for configuration change
        val alarmFromViewModel = viewModel.checkedAlarmType.value
        if (alarmFromViewModel != null) {
            val alarmModel = getAlarmModelFromAlarmType(alarmFromViewModel)
            renderUiState(UiState.Success(alarmModel))
            return
        }
        //알림 세팅에 한번이라도 들어온 적이 있는 경우
        val cachedAlarm = mainViewModel.alarmType.value
        if (cachedAlarm != null) {
            val alarmModel = getAlarmModelFromAlarmType(cachedAlarm)
            renderUiState(UiState.Success(alarmModel))
            return
        }
        //알림 세팅에 한번도 들어온 적이 없는 경우
        viewModel.getAlarmSetting()
    }

    private fun renderUiState(uiState: UiState<Alarm?>) {
        when (uiState) {
            is UiState.None -> {}
            is UiState.Loading -> {
//                if (viewModel.checkedAlarmType.value == null)
//                    showLoadingDialog(parentFragmentManager, requireContext())
            }

            is UiState.Success -> {
                closeDialog(parentFragmentManager)
                uiState.value?.let { alarmModel ->
                    val alarmType = getAlarmTypeFromAlarmModel(alarmModel)
                    saveAlarmCache(alarmType)
                    setCheckBoxChecked(alarmType)
                }
            }

            is UiState.Error -> {
                showErrorDialog(parentFragmentManager, uiState.errorMessage) {
                    viewModel.resetUiState()
                }
            }
        }
    }

    private fun renderSaveState(uiState: UiState<Alarm?>) {
        when (uiState) {
            is UiState.None -> {}
            is UiState.Loading -> {
            }

            is UiState.Success -> {
                val alarmType = getAlarmTypeFromAlarmModel(uiState.value!!)
                saveAlarmCache(alarmType)
                parentFragmentManager.popBackStack()
            }

            is UiState.Error -> {
                showErrorDialog(parentFragmentManager, uiState.errorMessage) {
                    viewModel.resetSaveState()
                }
            }
        }
    }

    private fun setCheckBoxChecked(alarmType: AlarmType) {
        alarmTypes.forEachIndexed { index, type ->
            checkBoxs[index]?.isChecked = type == alarmType
        }
    }

    private fun saveAlarm() {
        viewModel.checkedAlarmType.value?.let { alarmType ->
            val alarmModel = getAlarmModelFromAlarmType(alarmType)
            viewModel.postAlarmSetting(alarmModel)
        }
    }

    private fun saveAlarmCache(alarmType: AlarmType) {
        mainViewModel.setAlarmType(alarmType)
    }

    private fun setOnClickListener() {
        binding.btnOk.setOnClickListener {
            saveAlarm()
        }

        checkBoxs.forEachIndexed { index, checkBox ->
            checkBox?.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setCheckedItem(alarmTypes[index])
                    setCheckBoxChecked(alarmTypes[index])
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        setFragmentResult(
            LinkFragment.shouldRefreshPagingKey,
            bundleOf(LinkFragment.shouldRefreshPaging to false)
        )
        super.onDestroyView()
    }

}