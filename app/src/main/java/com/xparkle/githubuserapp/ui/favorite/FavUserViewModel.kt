package com.xparkle.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xparkle.githubuserapp.database.FavUser
import com.xparkle.githubuserapp.repository.FavUserRepository

class FavUserViewModel(application: Application): ViewModel() {
    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)

    fun getAllFavUser(): LiveData<List<FavUser>> = mFavUserRepository.getAllFavorites()
    fun unsetFavorite(username:String) {
        mFavUserRepository.unsetFavorite(username)
    }

    fun removeAllFavorites() {
        mFavUserRepository.removeAllFavorites()
    }
}