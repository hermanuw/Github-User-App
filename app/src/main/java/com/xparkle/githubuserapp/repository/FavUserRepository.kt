package com.xparkle.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.xparkle.githubuserapp.database.FavUser
import com.xparkle.githubuserapp.database.FavUserDao
import com.xparkle.githubuserapp.database.FavUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application : Application) {
    private val mFavUserDao: FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserRoomDatabase.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun getAllFavorites(): LiveData<List<FavUser>> = mFavUserDao.getAllFavorites()

    fun setFavorite(favorite: FavUser) {
        executorService.execute { mFavUserDao.insert(favorite) }
    }

    fun unsetFavorite(username:String) {
        executorService.execute { mFavUserDao.unsetFavorite(username) }
    }

    fun checkFavorite(username:String):LiveData<FavUser> = mFavUserDao.getFavUserByUsername(username)

    fun removeAllFavorites() {
        executorService.execute { mFavUserDao.removeAllFavorites() }
    }
}