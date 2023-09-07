package com.example.composetutorial.vk.presentation.post

import com.example.composetutorial.vk.domain.PostItem

sealed class FeedScreenState {

    object InitialState : FeedScreenState()
    object Error : FeedScreenState()
    object LoadingState : FeedScreenState()

    data class Posts(val posts : List<PostItem>,
                     val dataIsLoading : Boolean = false)
        : FeedScreenState()

}