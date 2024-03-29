package com.eunji.lookatthis.presentation.view.links

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.presentation.R
import com.eunji.lookatthis.presentation.databinding.FragmentLinkBinding
import com.eunji.lookatthis.presentation.view.BaseLinkFragment
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.alarm_setting.AlarmSettingFragment
import com.eunji.lookatthis.presentation.view.link_register.LinkRegisterFragment
import com.eunji.lookatthis.presentation.view.manage_bookmark.ManageBookmarkFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LinkFragment : BaseLinkFragment() {
    private var _binding: FragmentLinkBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            postFcmToken()
            getLinkFromOtherApp()
        }
    }

    private fun getLinkFromOtherApp() {
        val action = requireActivity().intent.action
        val type = requireActivity().intent.type
        if ("android.intent.action.SEND" == action && type != null && "text/plain" == type) {
            val link = requireActivity().intent.getStringExtra("android.intent.extra.TEXT")
            link?.let { url ->
                val bundle = Bundle().apply {
                    putString(linkFromOtherApp, url)
                }
                val linkRegisterFragment = LinkRegisterFragment()
                linkRegisterFragment.arguments = bundle
                replaceFragment(linkRegisterFragment)
            }
        }
    }

    private fun postFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            token?.let {
                (viewModel as LinkViewModel).postFcmToken(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = viewModels<LinkViewModel>().value
        recyclerView = binding.recyclerView
        layoutLoading = binding.layoutLoading
        layoutEmpty = binding.layoutEmpty
        toggleBookmarkView = { link, position ->
            toggleBookmarkView(link, position)
        }
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.app_name))
        setOnClickListener()
        setSwipeLayout()
        setFragmentResultListener(shouldRefreshPagingKey) { _, bundle ->
            val result = bundle.getBoolean(shouldRefreshPaging, false)
            if (!result) return@setFragmentResultListener
            binding.swipeRefreshLayout.isRefreshing = true
            adapter.refresh()
            scrollToTop()
        }
        setFragmentResultListener(resultFromManageBookmarkView) { _, bundle ->
            val bookmarkOffIds = bundle.getIntegerArrayList(bookmarkIds)
            val readIds = bundle.getIntegerArrayList(readIds)
            setResultBookmarkOff(bookmarkOffIds)
            setResultRead(readIds)
        }
    }

    private fun setResultBookmarkOff(bookmarkOffIds: ArrayList<Int>?) {
        bookmarkOffIds?.forEach { id ->
            val link = adapter.snapshot().items.firstOrNull { linkModel ->
                linkModel.linkId == id
            }
            link?.let { linkModel ->
                val position = adapter.snapshot().items.indexOf(linkModel)
                toggleBookmarkView(linkModel, position)
            }
        }
    }

    private fun setResultRead(readIds: ArrayList<Int>?) {
        readIds?.forEach { id ->
            val link = adapter.snapshot().items.firstOrNull { linkModel ->
                linkModel.linkId == id
            }
            link?.let { linkModel ->
                val position = adapter.snapshot().items.indexOf(linkModel)
                setReadView(position)
            }
        }
    }

    private fun setSwipeLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
            scrollToTop()
        }
    }

    private fun scrollToTop() {
        //submitData완료후에 0번째 아이템을 찾아가기 위함
        binding.recyclerView.postDelayed(1000) {
            binding.recyclerView.scrollToPosition(0)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun toggleBookmarkView(link: Link, position: Int) {
        adapter.snapshot()[position]?.isBookmarked = !link.isBookmarked
        adapter.notifyItemChanged(position)
    }

    private fun setOnClickListener() {
        binding.btnAlarmSetting.setOnClickListener {
            replaceFragment(AlarmSettingFragment())
        }
        binding.btnRegister.setOnClickListener {
            replaceFragment(LinkRegisterFragment())
        }
        binding.btnGoToBookmarkLink.setOnClickListener {
            replaceFragment(ManageBookmarkFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        const val shouldRefreshPagingKey = "shouldRefreshPagingKey"
        const val shouldRefreshPaging = "shouldRefreshPaging"
        const val resultFromManageBookmarkView = "toggleBookmarkKey"
        const val bookmarkIds = "bookmarkIds"
        const val readIds = "readIds"
        const val linkFromOtherApp = "linkFromOtherApp"
    }

}