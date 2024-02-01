package com.eunji.lookatthis.presentation.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eunji.lookatthis.presentation.MainActivity
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.FragmentMainBinding
import com.eunji.lookatthis.presentation.model.MainItemModel
import com.eunji.lookatthis.presentation.util.DisplayUnitUtil.dpToPx
import com.eunji.lookatthis.presentation.view.LinkRegisterFragment
import com.eunji.lookatthis.presentation.view.alarm_setting.AlarmSettingFragment

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.app_name))
        setRecyclerView()
        setOnClickListener()
    }

    private fun setRecyclerView() {
        val dummies = List(10)
        {
            MainItemModel(
                "https://i.namu.wiki/i/qMlTxHoLhsm3nCB-KVEPNLf85S50Sgs3fHZI-O_OaoOepBiHxeCn2JLUpfzZ2xv8xE7Xf0Y033CYsjQALyxJlgC2U--7HyX7ytaevt1eAEPxrV8Ybkdh76kLQOHNX0gvLRj-foT5xzzFVUGfoQA2NA.webp",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies aliquam, nisl nisl aliquet nisl, eu ultricies nisl nisl eget ultricies aliquam, nisl nisl aliquet nisl, eu ultricies nisl",
                "20231101",
                false,
                "https://www.naver.com"
            )
        }
        val paddingBottom = dpToPx(20f, requireContext())
        binding.recyclerView.adapter = MainAdapter(items = dummies)
        binding.recyclerView.addItemDecoration(MainRecyclerViewItemDecoration(paddingBottom))
    }

    private fun setOnClickListener() {
        binding.btnAlarmSetting.setOnClickListener {
            replaceToAlarmSettingFragment()
        }
        binding.btnRegister.setOnClickListener {
            replaceToRegisterFragment()
        }
    }

    private fun replaceToAlarmSettingFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AlarmSettingFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun replaceToRegisterFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LinkRegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}