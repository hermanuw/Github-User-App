package com.xparkle.githubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavUser)

    @Query("SELECT * FROM FavUser WHERE username = :username")
    fun getFavUserByUsername(username: String): LiveData<FavUser>

    @Query("DELETE FROM FavUser WHERE username = :username")
    fun unsetFavorite(username: String)

    @Query("DELETE FROM FavUser")
    fun removeAllFavorites()

    @Query("SELECT * from FavUser")
    fun getAllFavorites(): LiveData<List<FavUser>>
}