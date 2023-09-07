package com.example.composetutorial.vk.domain

data class CommentItem  (
    val id : Long,
    val authorName :String ,
    val avatarId : String ,
    val date : String ,
    val commentText : String
        )