package com.eunji.lookatthis.view.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.ItemMainBinding
import com.eunji.lookatthis.model.MainItemModel


class MainAdapter(private val items: List<MainItemModel>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageView.setOnClickListener {
                openUrl(items[adapterPosition].link, it.context)
            }
        }

        private fun openUrl(url: String, context: Context) {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(context, browserIntent, null)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemMainBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = items[position]
            Glide.with(imageView.context)
                .load(Uri.parse(item.image))
                .transform(
                    CenterCrop(),
                    RoundedCorners(
                        imageView.context.resources.getDimension(R.dimen.radius_8).toInt()
                    )
                )
                .into(imageView)
            tvContent.text = item.description
            tvDate.text = item.time
        }
    }

    override fun getItemCount(): Int = items.size
}