package com.example.composetutorial.vk.data.repository

import android.app.Application
import android.util.Log
import com.example.composetutorial.extentions.merge
import com.example.composetutorial.vk.data.model.mapper.PostMapper
import com.example.composetutorial.vk.data.net.VKRequest
import com.example.composetutorial.vk.domain.PostItem
import com.example.composetutorial.vk.domain.PostStatus
import com.example.composetutorial.vk.domain.StatisticType
import com.example.composetutorial.vk.domain.StatisticsItem
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepository(application: Application) {
    private val postMapper = PostMapper()
    private val scope = CoroutineScope(Dispatchers.Default)

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)


    private var _postsData = mutableListOf<PostItem>()
    private val postsData: List<PostItem>
        get() = _postsData.toList()

    private var nextFrom : String? = null

    private var RecomendTriggerFlow = MutableSharedFlow<Unit>(replay = 1)
    private var likeTriggerFlow = MutableSharedFlow<PostStatus>()

    val getRecommendations: StateFlow<PostStatus> = flow {
       RecomendTriggerFlow.emit(Unit)
        RecomendTriggerFlow.collect {
            val getNext = nextFrom

            if (getNext == null && postsData.isNotEmpty()) {  //если у ВК закончились рекомендации для нас
                emit(postsData)
            }
            val response = if (getNext == null) {
                VKRequest.apiService.getRecommendations(getAccessToken())
            } else {
                VKRequest.apiService.getRecommendations(getAccessToken(), getNext)
            }

            nextFrom = response.response.nextfrom
            val posts = postMapper.mapToPostItem(response)
            _postsData.addAll(posts)

            emit(postsData)
        }
    }
        .map { PostStatus.Success(it)  as PostStatus }
        .merge(likeTriggerFlow)
        .retry(retries = 1) {
            delay(2000)
            true
        }.catch {
            emit(PostStatus.Error)
        }
        .stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = PostStatus.Success(postsData)
    )


    suspend fun loadNextData(){
            RecomendTriggerFlow.emit(Unit)
    }

    private fun getAccessToken() =
        token?.accessToken ?: throw IllegalStateException("vk access token can't be null")


    suspend fun changeLike(feedpost: PostItem) {
        val response = if (feedpost.isLiked) {
            VKRequest.apiService.deleteLike(
                getAccessToken(),
                owner_id = feedpost.communityid,
                item_id = feedpost.id
            )

        } else {
            VKRequest.apiService.addLike(
                getAccessToken(),
                owner_id = feedpost.communityid,
                item_id = feedpost.id
            )
        }

        val statElements = feedpost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKE }
            add(StatisticsItem(StatisticType.LIKE, response.likes.count))
        }

        val newpost = feedpost.copy(statistics = statElements, isLiked = !feedpost.isLiked)
        val index = postsData.indexOf(feedpost)
        _postsData[index] = newpost
        likeTriggerFlow.emit(PostStatus.Success(postsData))
    }


}