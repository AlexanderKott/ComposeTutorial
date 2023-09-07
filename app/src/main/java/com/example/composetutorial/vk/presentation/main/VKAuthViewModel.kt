package com.example.composetutorial.vk.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.vk.data.repository.AuthRepository
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.launch

class VKAuthViewModel(application :Application) : AndroidViewModel(application) {

    private val repo = AuthRepository(application)

    val authState = repo.authState

    fun checkAuthState() {
        viewModelScope.launch {
            repo.authTrigger()
        }
    }
}