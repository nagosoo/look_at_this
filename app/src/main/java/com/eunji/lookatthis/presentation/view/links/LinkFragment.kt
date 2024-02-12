package com.eunji.lookatthis.presentation.view.links

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.eunji.lookatthis.R
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.databinding.FragmentMainBinding
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.presentation.util.DialogUtil
import com.eunji.lookatthis.presentation.util.DisplayUnitUtil.dpToPx
import com.eunji.lookatthis.presentation.view.MainActivity
import com.eunji.lookatthis.presentation.view.alarm_setting.AlarmSettingFragment
import com.eunji.lookatthis.presentation.view.link_register.LinkRegisterFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LinkFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LinkViewModel by viewModels()
    private val adapter by lazy { LinkAdapter(::read, ::bookmark) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState==null){
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

    var isfirst = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setAppBarTitle(getString(R.string.app_name))
        setPagingAdapter()
        setOnClickListener()
        init()
    }

    private fun init() {
        if (isfirst) {
            viewLifecycleOwner.lifecycleScope.launch {
                isfirst = false
                viewModel.getLinks()
                    .collectLatest { pagingData ->
                        adapter.submitData(lifecycle, pagingData)
                    }
            }
        }
    }

    private fun openUrl(url: String) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ContextCompat.startActivity(requireContext(), browserIntent, null)
    }

    private fun read(link: LinkModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            if(!link.isRead) setRead(link.linkId)
            viewModel.read(link.linkId)
                .collect {
                    openUrl(link.linkUrl)
                }
        }
    }

    private suspend fun setRead(linkId: Int) {
        adapter.submitData(
            PagingData.from(adapter.snapshot().items.map { link ->
                if (link.linkId == linkId) {
                    link.copy(
                        isRead = true
                    )
                } else link
            })
        )
    }

    private fun bookmark(linkId: Int, isBookmarked: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch {
            //Todo::깜빡이는 거해결
            //Todo::디바운스 넣기
            toggleBookmarkImage(linkId, isBookmarked)
            viewModel.bookmark(linkId).collect { uiState ->
                if (uiState is UiState.Error) {
                    DialogUtil.showErrorDialog(parentFragmentManager, uiState.errorMessage)
                    toggleBookmarkImage(linkId, !isBookmarked) //북마크 이미지 되돌리기
                }
            }
        }
    }

    private suspend fun toggleBookmarkImage(linkId: Int, isBookmarked: Boolean) {
        adapter.submitData(
            PagingData.from(adapter.snapshot().items.map { link ->
                if (link.linkId == linkId) {
                    link.copy(
                        isBookmarked = !isBookmarked
                    )
                } else link
            })
        )
    }

    private fun setPagingAdapter() {
        val paddingBottom = dpToPx(20f, requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(LinkRecyclerViewItemDecoration(paddingBottom))
        adapter.addLoadStateListener { combinedLoadStates ->
            if (combinedLoadStates.append.endOfPaginationReached) {
                if (adapter.itemCount < 1) {
                    binding.layoutEmpty.root.visibility = View.VISIBLE
                } else {
                    binding.layoutEmpty.root.visibility = View.GONE
                }
            }
        }
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