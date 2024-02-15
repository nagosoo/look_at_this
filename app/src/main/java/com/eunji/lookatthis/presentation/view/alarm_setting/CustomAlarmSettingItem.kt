package com.eunji.lookatthis.presentation.view.alarm_setting

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.CustomAlarmSettingItemBinding

class CustomAlarmSettingItem : LinearLayout {
    private lateinit var _textView: TextView
    val textView: TextView
        get() = _textView
    private lateinit var _checkBox: CheckBox
    val checkBox: CheckBox
        get() = _checkBox

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs) {
        initView()
        getAttrs(attrs, defStyle)
    }

    private fun initView() {
        val inflater = LayoutInflater.from(context)
        val binding = CustomAlarmSettingItemBinding.inflate(inflater, this, false)
        _textView = binding.textView
        _checkBox = binding.checkbox.root
        _textView.setOnClickListener {
            _checkBox.isChecked = !_checkBox.isChecked
        }
        addView(binding.root)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomAlarmSettingItem)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomAlarmSettingItem, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        this._textView.text =
            typedArray.getString(R.styleable.CustomAlarmSettingItem_text_alarm_time) ?: ""
        typedArray.recycle()
    }

}