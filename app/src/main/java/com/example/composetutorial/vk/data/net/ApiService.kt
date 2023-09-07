package com.example.composetutorial.vk.data.net

import com.example.composetutorial.vk.data.model.CommentsResposeDTO
import com.example.composetutorial.vk.data.model.LikesCountResponseDTO
import com.example.composetutorial.vk.data.model.NewsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun doRequestTest(@Query("access_token") token: String): String

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun getRecommendations(@Query("access_token") token : String) : NewsDTO

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun getRecommendations(
        @Query("access_token") token : String,
        @Query("next_from") nextFrom : String,
        ) : NewsDTO



     @GET("likes.add?v=5.131&type=post")
    suspend fun addLike(
        @Query("access_token") token : String,
        @Query("owner_id") owner_id : Long,
        @Query("item_id") item_id : Long)
    : LikesCountResponseDTO


    @GET("likes.delete?v=5.131&type=post")
    suspend fun deleteLike(
        @Query("access_token") token : String,
        @Query("owner_id") owner_id : Long,
        @Query("item_id") item_id : Long)
            : LikesCountResponseDTO


    @GET("wall.getComments?v=5.131&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token : String,
        @Query("owner_id") ownerId : Long,
        @Query("post_id") itemId : Long)
           : CommentsResposeDTO
           // : String
}

