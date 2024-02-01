package com.eunji.lookatthis.presentation.view.alarm_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentTimePickerDialogBinding

class TimepickerDialog : DialogFragment() {

    private var _binding: FragmentTimePickerDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlarmSettingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.TimePickerDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimePickerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnOk.setOnClickListener {
            setTime()
            dismiss()
        }
    }

    private fun setTime() {
        viewModel.setTime(binding.timePicker.hour, binding.timePicker.minute)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "TimerPickerDialog"
    }

}