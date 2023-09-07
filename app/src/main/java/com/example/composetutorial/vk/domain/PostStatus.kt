package com.example.composetutorial.vk.domain

sealed class PostStatus {

    object Error : PostStatus()
    data class Success(val posts: List<PostItem>) : PostStatus()

}