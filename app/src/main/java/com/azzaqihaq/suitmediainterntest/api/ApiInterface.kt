package com.azzaqihaq.suitmediainterntest.api

import com.azzaqihaq.suitmediainterntest.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("users")
    fun getUsers(
        @Query("page") page: Int?,
    ): Call<UserResponse>

}