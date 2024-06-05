package com.xparkle.githubuserapp.helper

import com.xparkle.githubuserapp.database.FavUser
import androidx.recyclerview.widget.DiffUtil

class FavUserDiffCallback (
        private val mOldFavoriteList: List<FavUser>,
        private val mNewFavoriteList: List<FavUser>
        ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return mOldFavoriteList.size
        }

        override fun getNewListSize(): Int {
            return mNewFavoriteList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldFavoriteList[oldItemPosition].username == mNewFavoriteList[newItemPosition].username
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldFavorite = mOldFavoriteList[oldItemPosition]
            val newFavorite = mNewFavoriteList[newItemPosition]
            return oldFavorite.username == newFavorite.username
        }
}