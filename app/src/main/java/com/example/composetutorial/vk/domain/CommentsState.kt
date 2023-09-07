package com.example.composetutorial.vk.domain

sealed class CommentsState {

    object  Error : CommentsState()
    object  Init : CommentsState()

    data class Success(val list : List<CommentItem>) : CommentsState()

}