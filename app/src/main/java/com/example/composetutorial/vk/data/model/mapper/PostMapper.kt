package com.example.composetutorial.vk.data.model.mapper

import com.example.composetutorial.vk.data.model.CommentsResposeDTO
import com.example.composetutorial.vk.data.model.NewsDTO
import com.example.composetutorial.vk.domain.CommentItem
import com.example.composetutorial.vk.domain.PostItem
import com.example.composetutorial.vk.domain.StatisticType
import com.example.composetutorial.vk.domain.StatisticsItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class PostMapper {

    fun dateFormat(value : Long) : String {
        val date = Date(value * 1000)
        return SimpleDateFormat("yyyy-MM-dd HH:mm" , Locale.getDefault()).format(date)
    }


    fun mapToPostItem(dto :NewsDTO) : List<PostItem>{
        val result = ArrayList<PostItem>()
        for (post in dto.response.posts){
            val tempItem = dto.response.groupNames.find { it.id == post.communityId.absoluteValue }

            val item = PostItem(
                id = post.id,
                communityid = post.communityId,
                name = tempItem?.name ?: continue,
                time =  dateFormat(post.date) ,
                postAva = tempItem?.image ?: continue,
                text = post.text,
                imageAttach = post.attachments?.find { it.type == "photo" }?.photo?.sizes?.find { it.type == "y" }?.url ?:"",
                statistics = listOf(
                    StatisticsItem(StatisticType.COMMENT, post.comments.count),
                    StatisticsItem(StatisticType.LIKE, post.likes.count),
                    StatisticsItem(StatisticType.REPLY, post.reposts.count),
                    StatisticsItem(StatisticType.VIEW, post.views.count),

                ),
                 isLiked = post.likes.isLiked == 1
            )

            result.add(item)
        }

        return result

    }

    fun mapToCommentItem(response: CommentsResposeDTO): List<CommentItem> {
        val newComments = mutableListOf<CommentItem>()
        val comments = response.response.comments
        val users = response.response.users

        for (comment in comments){
            val author = users.firstOrNull {it.id == comment.authorID } ?: continue
            val postComment = CommentItem(
                id = comment.id.toLong(),
                authorName =  "${author.firstname} ${author.lastname}",
                avatarId = author.avatarURL,
                commentText = comment.text,
                date = dateFormat(comment.date.toLong())

            )
            newComments.add(postComment)
        }
        return newComments
    }
}