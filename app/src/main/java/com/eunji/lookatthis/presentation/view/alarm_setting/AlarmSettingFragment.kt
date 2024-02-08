package com.eunji.lookatthis.presentation.view.alarm_setting

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat.jumpDrawablesToCurrentState
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eunji.lookatthis.R
import com.eunji.lookatthis.data.model.AlarmModel
import com.eunji.lookatthis.databinding.FragmentAlarmSettingBinding
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.presentation.model.AlarmType
import com.eunji.lookatthis.presentation.util.AlarmUtil.getAlarmModelFromAlarmType
import com.eunji.lookatthis.presentation.util.AlarmUtil.getAlarmTypeFromAlarmModel
import com.eunji.lookatthis.presentation.util.DialogUtil.closeDialog
import com.eunji.lookatthis.presentation.util.DialogUtil.showErrorDialog
import com.eunji.lookatthis.presentation.util.DialogUtil.showLoadingDialog
import com.eunji.lookatthis.presentation.view.CommonDialog
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.MainViewModel
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
    }

    private fun init() {
        mainViewModel.alarmType.value?.let { alarmType ->
            val alarmModel = getAlarmModelFromAlarmType(alarmType)
            renderGetAlarmApiResult(UiState.Success(alarmModel))
            return
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAlarmSetting().collect { uiState ->
                renderGetAlarmApiResult(uiState)
            }
        }
    }

    private fun renderGetAlarmApiResult(uiState: UiState<AlarmModel?>) {
        when (uiState) {
            is UiState.Loading -> {
                showLoadingDialog(parentFragmentManager, requireContext())
            }

            is UiState.Success -> {
                closeDialog(parentFragmentManager)
                uiState.value?.let { alarmModel ->
                    val alarmType = getAlarmTypeFromAlarmModel(alarmModel, alarmTypes)
                    saveAlarmCache(alarmType)
                    setCheckBoxChecked(alarmType)
                }
            }

            is UiState.Error -> {
                showErrorDialog(parentFragmentManager, uiState.errorMessage)
            }
        }
    }

    private fun renderPostAlarmResult(uiState: UiState<AlarmModel?>, alarmType: AlarmType) {
        when (uiState) {
            is UiState.Loading -> {
            }

            is UiState.Success -> {
                saveAlarmCache(alarmType)
                parentFragmentManager.popBackStack()
            }

            is UiState.Error -> {
                showErrorDialog(parentFragmentManager, uiState.errorMessage)
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
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.postAlarmSetting(alarmModel).collect { uiState ->
                    renderPostAlarmResult(uiState, alarmType)
                }
            }
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
                    binding.customItem15Pm.checkBox.isChecked = true 
                    viewModel.setCheckedItem(alarmTypes[index])
                    setCheckBoxChecked(alarmTypes[index])
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}