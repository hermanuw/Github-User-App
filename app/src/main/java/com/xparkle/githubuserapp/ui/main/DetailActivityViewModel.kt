package com.xparkle.githubuserapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xparkle.githubuserapp.data.response.DetailUserResponse
import com.xparkle.githubuserapp.data.retrofit.ApiConfig
import com.xparkle.githubuserapp.database.FavUser
import com.xparkle.githubuserapp.repository.FavUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityViewModel(application: Application): ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailViewModel"
    }

    init {
        findDetailUser("")
    }

    private val mUserFavoriteRepository = FavUserRepository(application)
    val userData = MutableLiveData<DetailUserResponse>()
    val userFavorite = MutableLiveData<FavUser>()
    val error = MutableLiveData<String>()
    val isFavorite = MutableLiveData<Boolean>()

    fun setFavorite(favorite: FavUser) {
        mUserFavoriteRepository.setFavorite(favorite)
        isFavorite.postValue(true)
    }

    fun unsetFavorite(username:String) {
        mUserFavoriteRepository.unsetFavorite(username)
        isFavorite.postValue(false)
    }

    // inisialisasi sementara data untuk favorite
    fun initFavorite(favorite: FavUser) {
        userFavorite.postValue(favorite)
    }

    // jalankan query untuk check apakah user di like
    fun checkFavorite(username: String): LiveData<FavUser> =
        mUserFavoriteRepository.checkFavorite(username)


    fun findDetailUser(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}

