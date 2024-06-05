package com.xparkle.githubuserapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xparkle.githubuserapp.data.response.ItemsItem
import com.xparkle.githubuserapp.databinding.RowUserBinding

class UserAdapter(private val onItemClickListener: OnItemClickListener): ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
        interface OnItemClickListener {
            fun onItemClick(user: ItemsItem)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val binding = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val user = getItem(position)
            holder.bind(user)
            holder.itemView.setOnClickListener {
                onItemClickListener.onItemClick(user)
            }
        }

        class MyViewHolder(private val binding: RowUserBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(user: ItemsItem) {
                binding.tvUsername.text = user.login
                Glide.with(binding.root)
                    .load(user.avatarUrl)
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