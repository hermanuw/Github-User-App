package com.xparkle.githubuserapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xparkle.githubuserapp.data.response.ItemsItem
import com.xparkle.githubuserapp.databinding.RowUserBinding

class FollowAdapter : ListAdapter<ItemsItem, FollowAdapter.ViewHolder>(DIFF_CALLBACK) {
    private var listData: List<ItemsItem>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ItemsItem>?){
        listData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowAdapter.ViewHolder, position: Int) {
        val data = listData?.get(position)
        if (data != null) holder.bind(data)
    }

    override fun getItemCount(): Int = listData?.size?:0

    class ViewHolder(private val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem?) {
            binding.tvUsername.text = data?.login
            Glide.with(itemView.context)
                .load(data?.avatarUrl)
                .circleCrop()
                .into(binding.imageView)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}