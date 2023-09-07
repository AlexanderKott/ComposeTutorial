package com.example.composetutorial.vk.presentation.main

import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composetutorial.R
import com.example.composetutorial.sample.instagram.VKTheme
import com.example.composetutorial.sumin.VKFeedPage
import com.example.composetutorial.sumin.vk.presentation.post.LoginScreen
import com.example.composetutorial.vk.domain.VKAuthState
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope


data class Message(val author: String, val message: String)


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setTheme(R.style.Theme_ComposeTutorial)
        setContent {
            VKTheme {

                val avm: VKAuthViewModel = viewModel()
                val authState = avm.authState.collectAsState(VKAuthState.Initial)

                val vkAuth = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract(),
                    onResult = {
                        avm.checkAuthState()
                    })

                when (authState.value) {
                    VKAuthState.IsAuthorized -> VKFeedPage()
                    VKAuthState.IsNotAuthorized -> LoginScreen {
                        vkAuth.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                    }
                    VKAuthState.Initial -> {}

                }
            }
        }
    }
}




