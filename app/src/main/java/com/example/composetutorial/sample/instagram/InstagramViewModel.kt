package com.example.composetutorial.sample.instagram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InstagramViewModel : ViewModel(){

 private val _isFollowing  = MutableLiveData<Boolean>()
 val isFollowing: LiveData<Boolean> = _isFollowing

    fun changeFollowState() {
        _isFollowing.value = !(_isFollowing.value ?: false)
    }

}