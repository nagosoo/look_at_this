package com.eunji.lookatthis.presentation.adapter

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
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.presentation.R
import com.eunji.lookatthis.presentation.databinding.ItemMainBinding
import com.eunji.lookatthis.presentation.util.DateUtil


class LinkAdapter(
    private val read: (Link, Int) -> Unit,
    private val bookmark: (Link, Int) -> Unit
) :
    PagingDataAdapter<Link, LinkAdapter.ViewHolder>(DIFF_UTIL) {

    inner class ViewHolder(val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(link: Link) {
            with(binding) {
                imageView.setOnClickListener {
                    read(link, absoluteAdapterPosition)
                }
                ivLike.setOnClickListener {
                    bookmark(link, absoluteAdapterPosition)
                }
                setImage(link)
                setMemo(link)
                setReadOrNot(link.isRead)
                setBookmarkOrNot(link.isBookmarked)
                setDate(link.linkCreatedAt)
            }
        }

        private fun setDate(date: String) {
            binding.tvDate.text = DateUtil.convertDateFormat(date)
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

        private fun setMemo(link: Link) {
            if (link.linkMemo?.isNotBlank() == true) {
                binding.tvContent.text = link.linkMemo
            } else {
                binding.tvContent.text =
                    binding.tvContent.context.getString(R.string.text_empty_memo)
            }
        }

        private fun setImage(link: Link) {
            val hasThumbnail = link.linkThumbnail?.isNotBlank() == true
            if (hasThumbnail) {
                setThumbnail(link.linkThumbnail!!)
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
                    .placeholder(image)
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
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Link>() {
            override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
                return oldItem.linkId == newItem.linkId
            }

            override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
                return oldItem == newItem
            }
        }
    }

}