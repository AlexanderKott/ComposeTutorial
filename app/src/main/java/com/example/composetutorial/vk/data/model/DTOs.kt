package com.example.composetutorial.vk.data.model

import com.google.gson.annotations.SerializedName


data class NewsDTO(
    @SerializedName("response") val response :  ResponseDTO,
)


data class CommentProfileDTO(
    @SerializedName("id") val id : String,
    @SerializedName("first_name") val firstname : String,
    @SerializedName("last_name") val lastname : String,
    @SerializedName("photo_100") val avatarURL : String,
)

data class CommentDTO(
    @SerializedName("id") val id : String,
    @SerializedName("from_id") val authorID : String,
    @SerializedName("text") val text : String,
    @SerializedName("date") val date : String,
)

data class CommentsContentDTO(
    @SerializedName("items") val comments : List<CommentDTO>,
    @SerializedName("profiles") val users : List<CommentProfileDTO>,
)

data class CommentsResposeDTO(
    @SerializedName("response") val response : CommentsContentDTO,
)



data class ResponseDTO(
    @SerializedName("items") val posts : List<PostDTO>,
    @SerializedName("profiles") val usersNames : List<ProfilesDTO>,
    @SerializedName("groups") val groupNames : List<GroupDTO>,
    @SerializedName("next_from") val nextfrom : String?,
)


data class ProfilesDTO(
    @SerializedName("id") val id : String,
    @SerializedName("first_name") val firstname : String,
    @SerializedName("last_name") val lastname : String,
)

data class GroupDTO(
    @SerializedName("id") val id : Long,
    @SerializedName("name") val name : String,
    @SerializedName("photo_100") val image : String,


)


data class LikesCountDTO(
    @SerializedName("likes") val count : Long)

data class LikesCountResponseDTO(
    @SerializedName("response") val likes : LikesCountDTO)


//items
data class PostDTO(
    @SerializedName("id") val id : Long,
    @SerializedName("text") val text : String,
    @SerializedName("date") val date : Long,
    @SerializedName("source_id") val communityId : Long,
    @SerializedName("comments") val comments : CommentsDTO,
    @SerializedName("likes") val likes : LikesDTO,
    @SerializedName("reposts") val reposts : RepostDTO,
    @SerializedName("views") val views : ViewsDTO,
    @SerializedName("attachments") val attachments : List<AttachmentDTO>?,


    )

data class ViewsDTO(
    @SerializedName("count") val count : Long
)


data class AttachmentDTO(
    @SerializedName("type")  val type: String,
    @SerializedName("photo") val photo: PhotoDTO?,
    @SerializedName("video") val video: VideoDTO?,
)


data class VideoDTO(
    @SerializedName("title")  val title: String,
)

data class PhotoDTO(
    @SerializedName("post_id")  val type: String,
    @SerializedName("sizes") val sizes: List<PhotoSizesDTO>,
)

data class PhotoSizesDTO(
    @SerializedName("type")  val type: String,
    @SerializedName("url") val url: String,
)


data class CommentsDTO(
    @SerializedName("can_post")  val canPost: Int,
    @SerializedName("count") val count : Long
    )


data class RepostDTO(
    @SerializedName("count") val count : Long
)

data class LikesDTO(
    @SerializedName("can_like")  val canLike: Int,
    @SerializedName("count") val count : Long,
    @SerializedName("user_likes") val isLiked : Int


)

