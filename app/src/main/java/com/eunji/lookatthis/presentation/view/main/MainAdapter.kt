package com.eunji.lookatthis.presentation.view.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.eunji.lookatthis.R
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.databinding.ItemMainBinding


class MainAdapter(private val onItemClickListener: (String) -> Unit) :
    PagingDataAdapter<LinkModel, MainAdapter.ViewHolder>(DIFF_UTIL) {

    inner class ViewHolder(val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(linkModel: LinkModel) {
            with(binding) {
                imageView.setOnClickListener {
                    onItemClickListener(linkModel.linkUrl)
                }
                setImage(linkModel)
                setMemo(linkModel)
                setReadOrNot(linkModel.linkIsRead)
                setBookmarkOrNot(linkModel.linkIsBookmark)
                tvDate.text = linkModel.linkCreatedAt
            }
        }

        private fun setBookmarkOrNot(isBookmarked: Boolean) {
            if (isBookmarked) {
                binding.ivLike.alpha = 1f
            } else {
                binding.ivLike.alpha = 0.3f
            }
        }

        private fun setReadOrNot(isRead: Boolean) {
            if (isRead) {
                setTextColor(
                    ContextCompat.getColor(
                        binding.tvContent.context,
                        R.color.grey_dark
                    )
                )
            } else {
                setTextColor(
                    ContextCompat.getColor(
                        binding.tvContent.context,
                        R.color.black
                    )
                )
            }
        }

        private fun setTextColor(color: Int) {
            binding.tvContent.setTextColor(color)
            binding.tvDate.setTextColor(color)
        }

        private fun setMemo(linkModel: LinkModel) {
            if (linkModel.linkMemo?.isNotBlank() == true) {
                binding.tvContent.text = linkModel.linkMemo
            } else {
                binding.tvContent.text =
                    binding.tvContent.context.getString(R.string.text_empty_memo)
            }
        }

        private fun setImage(linkModel: LinkModel) {
            if (linkModel.linkThumbnail?.isNotBlank() == true) {
                setThumbnail(linkModel.linkThumbnail)
            } else {
                setDefaultImage()
            }
        }

        private fun setThumbnail(thumbnail: String) {
            with(binding.imageView) {
                Glide.with(context)
                    .load(Uri.parse(thumbnail))
                    .transform(
                        CenterCrop(),
                        RoundedCorners(
                            context.resources.getDimension(R.dimen.radius_8).toInt()
                        )
                    )
                    .into(this)
            }
        }

        private fun setDefaultImage() {
            with(binding.imageView) {
                val image = ContextCompat.getDrawable(context, R.drawable.splash)
                Glide.with(context)
                    .load(image)
                    .into(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemMainBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { link ->
            holder.bind(link)
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<LinkModel>() {
            override fun areItemsTheSame(oldItem: LinkModel, newItem: LinkModel): Boolean {
                return oldItem.linkId == newItem.linkId
            }

            override fun areContentsTheSame(oldItem: LinkModel, newItem: LinkModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}