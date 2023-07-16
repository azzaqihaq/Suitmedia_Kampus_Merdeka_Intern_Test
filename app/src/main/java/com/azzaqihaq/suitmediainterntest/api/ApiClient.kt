package com.azzaqihaq.suitmediainterntest.api

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object ApiClient {

    val BASE_URL = "https://reqres.in/api/"

    fun getRetroData(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}