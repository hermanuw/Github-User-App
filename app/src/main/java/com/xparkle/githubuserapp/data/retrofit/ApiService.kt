package com.xparkle.githubuserapp.data.retrofit

import com.xparkle.githubuserapp.data.response.DetailUserResponse
import com.xparkle.githubuserapp.data.response.GithubResponse
import com.xparkle.githubuserapp.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object{
        private const val USERNAME = "manu"
    }
    @GET("search/users")
//    @Headers("Authorization: token asdfghjkl")
    fun getSearchUsers(
        @Query("q") query: String = USERNAME
    ): Call<GithubResponse>

    @GET("users/{username}")
//    @Headers("Authorization: token asdfghjkl")
    fun getDetailUser(
        @Path("username") username: String = USERNAME
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
//    @Headers("Authorization: token asdfghjkl")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
//    @Headers("Authorization: token asdfghjkl")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}