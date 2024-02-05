package com.eunji.lookatthis.presentation.view.alarm_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentAlarmSettingBinding
import com.eunji.lookatthis.presentation.util.TimeUtil

class AlarmSettingFragment : Fragment() {
    private var _binding: FragmentAlarmSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlarmSettingViewModel by activityViewModels()

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
        setSwitch()
        setOnClickListener()
        setObserver()
    }

    private fun setSwitch() {
        binding.switchEveryTime.toggle.setOnCheckedChangeListener { _, isChecked ->
            binding.switchEveryTime.tvOff.isVisible = !isChecked
            binding.switchEveryTime.tvOn.isVisible = isChecked
            binding.switchOnlyOnce.toggle.isChecked = !isChecked
            setTimeTextColor(isChecked)
        }

        binding.switchOnlyOnce.toggle.setOnCheckedChangeListener { _, isChecked ->
            binding.switchOnlyOnce.tvOff.isVisible = !isChecked
            binding.switchOnlyOnce.tvOn.isVisible = isChecked
            binding.switchEveryTime.toggle.isChecked = !isChecked
            setTimeTextColor(!isChecked)
        }
    }

    private fun setTimeTextColor(isEveryTimeChecked: Boolean) {
        val color = if (isEveryTimeChecked) R.color.grey_dark else R.color.black
        binding.tvTimeOnlyOnce.setTextColor(requireContext().getColor(color))
    }

    private fun setOnClickListener() {
        binding.tvSwitchOnlyOnce.setOnClickListener {
            showTimePickerDialog()
        }
        binding.btnOk.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun showTimePickerDialog() {
        TimepickerDialog().show(childFragmentManager, TimepickerDialog.TAG)
    }

    private fun setObserver() {
        viewModel.time.observe(viewLifecycleOwner) { time ->
            binding.tvTimeOnlyOnce.text =
                "${TimeUtil.getHour(time)}:${TimeUtil.getMinute(time)}${TimeUtil.getAmPm(time)}"
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}