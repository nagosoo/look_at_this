package com.eunji.lookatthis.presentation.view.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
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
                Glide.with(imageView.context)
                    .load(Uri.parse(linkModel.linkThumbnail))
                    .transform(
                        CenterCrop(),
                        RoundedCorners(
                            imageView.context.resources.getDimension(R.dimen.radius_8).toInt()
                        )
                    )
                    .into(imageView)
                tvContent.text = linkModel.linkMemo
                tvDate.text = linkModel.linkCreatedAt
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