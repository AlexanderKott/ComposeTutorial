package com.example.composetutorial.vk.data.net

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.POST
import retrofit2.http.Query

object VKRequest {

    private val client = OkHttpClient.Builder()
        .addInterceptor( HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

   private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.vk.com/method/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    val apiService : ApiService = retrofit.create()


}