package com.example.composetutorial.vk.presentation.comments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.vk.data.repository.CommentsRepository
import com.example.composetutorial.vk.domain.CommentItem
import com.example.composetutorial.vk.domain.CommentsState
import com.example.composetutorial.vk.domain.PostItem
import com.example.composetutorial.vk.presentation.post.FeedScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VKCommentsViewModel(application : Application) : AndroidViewModel(application) {
    private val commentsRepo = CommentsRepository(application)

    private val handler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            Log.e("eeeee","CoroutineExceptionHandler getComments")
        }
    }

    val comments = MutableSharedFlow<CommentsState>()

      fun getComments(feedPost: PostItem) {
          viewModelScope.launch(handler) {
              commentsRepo.getComments(feedPost).collect {
                  comments.emit(it)
                  }

          }

    }



}