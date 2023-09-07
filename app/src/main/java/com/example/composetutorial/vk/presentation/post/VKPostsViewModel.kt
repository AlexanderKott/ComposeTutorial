package com.example.composetutorial.vk.presentation.post

import android.app.Application
import androidx.lifecycle.*
import com.example.composetutorial.extentions.merge
import com.example.composetutorial.vk.data.repository.NewsFeedRepository
import com.example.composetutorial.vk.domain.PostItem
import com.example.composetutorial.vk.domain.PostStatus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class VKPostsViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = NewsFeedRepository(application)

    private val nextDataPortionTrigger = MutableSharedFlow<Unit>()
    private val localDeleteData = MutableSharedFlow<FeedScreenState>()
    private val userActionsOnFeedList = MutableSharedFlow<FeedScreenState>()
    private val recommendationsFlow = repo.getRecommendations

    private val handler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            userActionsOnFeedList.emit(FeedScreenState.Error)
        }
    }

    private val nextDataPortion = flow<FeedScreenState> {
        nextDataPortionTrigger.collect {

            when (recommendationsFlow.value) {
                is PostStatus.Error -> {
                    emit(FeedScreenState.Error)
                }

                is PostStatus.Success -> {
                    val value = recommendationsFlow.value
                    if (value is PostStatus.Success) {
                        emit(
                            FeedScreenState.Posts(
                                posts = value.posts,
                                dataIsLoading = true
                            )
                        )
                    }
                }
            }
        }
    }

    val screenStateFlow: Flow<FeedScreenState> = recommendationsFlow
        .filter {
            when (it) {
                is PostStatus.Success -> it.posts.isNotEmpty()
                else -> true
            }

        } .map {
            when (it) {
                is PostStatus.Success -> FeedScreenState.Posts(it.posts)
                is PostStatus.Error -> FeedScreenState.Error
            }

        }
        .onStart { FeedScreenState.LoadingState }
         .merge(localDeleteData)
         .merge(nextDataPortion)
         .merge(userActionsOnFeedList)



    fun loadNextRecommendations() {
        viewModelScope.launch {
            nextDataPortionTrigger.emit(Unit)
            repo.loadNextData()
        }

    }

    fun setLike(post: PostItem) {
        viewModelScope.launch(handler) {
            repo.changeLike(post)
        }
    }

    fun deleteItem(postID: Long) {
        val state = recommendationsFlow.value

        if (state is PostStatus.Success){
            val value = state.posts
            if (!value.isNullOrEmpty()) {
                val newPosts = value.toMutableList().apply {
                    removeAll { it.id == postID }
                }
                viewModelScope.launch {
                    localDeleteData.emit(FeedScreenState.Posts(newPosts))
                }
        }
        } else {
            return
        }
    }

}