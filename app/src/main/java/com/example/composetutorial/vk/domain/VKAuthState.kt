package com.example.composetutorial.vk.domain

sealed class VKAuthState {

    object Initial : VKAuthState()
    object IsAuthorized : VKAuthState()
    object IsNotAuthorized: VKAuthState()

}