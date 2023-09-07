package com.example.composetutorial.vk.data.repository

import android.app.Application
import android.util.Log
import com.example.composetutorial.vk.data.model.mapper.PostMapper
import com.example.composetutorial.vk.data.net.VKRequest
import com.example.composetutorial.vk.domain.CommentItem
import com.example.composetutorial.vk.domain.CommentsState
import com.example.composetutorial.vk.domain.PostItem
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

class CommentsRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val postMapper = PostMapper()

    private val scope = CoroutineScope(Dispatchers.Default)

    private fun getAccessToken() =
        token?.accessToken ?: throw IllegalStateException("vk access token can't be null")

    suspend fun getComments(feedPost: PostItem) : Flow<CommentsState> = flow {
        val response = VKRequest.apiService.getComments(
            getAccessToken(),
            ownerId = feedPost.communityid,
            itemId = feedPost.id
        )
         emit(CommentsState.Success(postMapper.mapToCommentItem(response)) as CommentsState)
    }
        .retry(1) {
            delay(1000)

            true
        }.catch {
            emit(CommentsState.Error)
        }
        .shareIn(
        started = SharingStarted.Lazily,
        scope = scope
    )

}