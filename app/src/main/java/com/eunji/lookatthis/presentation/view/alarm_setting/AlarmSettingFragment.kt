package com.eunji.lookatthis.presentation.view.alarm_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentAlarmSettingBinding
import com.eunji.lookatthis.presentation.model.AlarmType
import com.eunji.lookatthis.presentation.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmSettingFragment : Fragment() {
    private var _binding: FragmentAlarmSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlarmSettingViewModel by activityViewModels()
    private val alarmTypes = AlarmType.values().toList()

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
        setRadioButton()
        setObserver()
    }

    private fun setObserver() {
        viewModel.checkedItem.observe(viewLifecycleOwner) { alarmType ->
            alarmTypes.filterNot { type ->
                type == alarmType
            }.forEach {
                unCheck(it)
            }
        }
    }

    private fun unCheck(alarmType: AlarmType) {
        val checkBox = when (alarmType) {
            AlarmType.EVERY_TIME -> binding.customItemEveryTime.checkBox
            AlarmType.AM11 -> binding.customItem11Am.checkBox
            AlarmType.PM15 -> binding.customItem15Pm.checkBox
            AlarmType.PM20 -> binding.customItem20Pm.checkBox
        }
        checkBox.isChecked = false
    }

    private fun setOnClickListener() {
        binding.btnOk.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setRadioButton() {
        binding.customItemEveryTime.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setCheckedItem(AlarmType.EVERY_TIME)
        }
        binding.customItem11Am.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setCheckedItem(AlarmType.AM11)
        }
        binding.customItem15Pm.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setCheckedItem(AlarmType.PM15)
        }
        binding.customItem20Pm.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setCheckedItem(AlarmType.PM20)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}