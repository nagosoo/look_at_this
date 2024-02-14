package com.eunji.lookatthis.presentation.view.links

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import com.eunji.lookatthis.R
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.databinding.FragmentMainBinding
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.presentation.util.DialogUtil
import com.eunji.lookatthis.presentation.util.DisplayUnitUtil.dpToPx
import com.eunji.lookatthis.presentation.util.UrlOpenUtil.openUrl
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.alarm_setting.AlarmSettingFragment
import com.eunji.lookatthis.presentation.view.link_register.LinkRegisterFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LinkFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LinkViewModel by viewModels()
    private val adapter by lazy { LinkAdapter(::read, ::bookmark) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            postFcmToken()
        }
    }

    private fun postFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            viewModel.postFcmToken(task.result)
        })
    }

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
        setPagingAdapter()
        setOnClickListener()
        setSwipeLayout()
        init()
        setFragmentResultListener(requestKey) { _, bundle ->
            val result = bundle.getBoolean(shouldRefreshPaging, false)
            if (!result) return@setFragmentResultListener
            binding.swipeRefreshLayout.isRefreshing = true
            adapter.refresh()
            scrollToTop()
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
        binding.recyclerView.postDelayed(500) {
            binding.recyclerView.scrollToPosition(0)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun init() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.links
                    .collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
            }
        }
    }

    private fun read(link: LinkModel, position: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            if (!link.isRead) setRead(position)
            viewModel.read(link.linkId)
                .collect {
                    openUrl(link.linkUrl, requireContext(), parentFragmentManager)
                }
        }
    }

    private fun setRead(position: Int) {
        adapter.snapshot()[position]?.isRead = true
        adapter.notifyItemChanged(position)
    }

    private fun bookmark(link: LinkModel, position: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            //Todo::깜빡이는 거해결
            //Todo::디바운스 넣기
            toggleBookmarkImage(link, position)
            viewModel.bookmark(link.linkId).collect { uiState ->
                if (uiState is UiState.Error) {
                    DialogUtil.showErrorDialog(parentFragmentManager, uiState.errorMessage)
                    toggleBookmarkImage(link, position) //북마크 이미지 되돌리기
                }
            }
        }
    }

    private fun toggleBookmarkImage(link: LinkModel, position: Int) {
        adapter.snapshot()[position]?.isBookmarked = !link.isBookmarked
        adapter.notifyItemChanged(position)
    }

    private fun setPagingAdapter() {
        val paddingBottom = dpToPx(20f, requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(LinkRecyclerViewItemDecoration(paddingBottom))
        adapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isEmpty =
                adapter.itemCount < 1 && loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached
            binding.layoutEmpty.root.isVisible = isEmpty
            val isError: LoadState.Error? =
                loadState.append as? LoadState.Error ?: loadState.refresh as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            isError?.let {
                DialogUtil.showErrorDialog(parentFragmentManager, "잠시 후 다시 시도해줘")
            }
        }
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LinkLoadStateAdapter()
        )
        //새로운 아이템이 추가되면 맨 첫번째 아이템으로 이동 시킴
//        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                super.onItemRangeInserted(positionStart, itemCount)
//                if (positionStart == 0) {
//                    //binding.recyclerView.scrollToPosition(0)
//                }
//            }
//        })
    }

    private fun setOnClickListener() {
        binding.btnAlarmSetting.setOnClickListener {
            replaceToAlarmSettingFragment()
        }
        binding.btnRegister.setOnClickListener {
            replaceToRegisterFragment()
        }
        binding.btnShowType.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.links.map {
                    it.filter {
                        it.isBookmarked
                    }
                }.collect {
                    adapter.submitData(it)
                }
            }
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

    companion object {
        const val requestKey = "requestKey"
        const val shouldRefreshPaging = "shouldRefreshPaging"
    }

}