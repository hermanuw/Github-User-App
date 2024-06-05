package com.xparkle.githubuserapp.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xparkle.githubuserapp.database.FavUser
import com.xparkle.githubuserapp.databinding.RowUserBinding
import com.xparkle.githubuserapp.helper.FavUserDiffCallback
import com.xparkle.githubuserapp.ui.main.DetailActivity

class FavUserAdapter : RecyclerView.Adapter<FavUserAdapter.FavoriteViewHolder>(){
    private val listFavorites = ArrayList<FavUser>()

    fun setListFavorites(listFavorites: List<FavUser>) {
        val diffCallback = FavUserDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getSwipedUsername(position: Int): String {
        return listFavorites[position].username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    inner class FavoriteViewHolder(private val binding: RowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavUser) {
            with(binding) {
                tvUsername.text = favorite.username
                Glide.with(itemView)
                    .load(favorite.avatarUrl)
                    .into(imageView)
                itemView.setOnClickListener {
                    val moveDetailActivity = Intent(
                        it.context,
                        DetailActivity::class.java
                    )
                    moveDetailActivity.putExtra("USERNAME", favorite.username)
                    it.context.startActivity(moveDetailActivity)
                }
            }
        }
    }

}