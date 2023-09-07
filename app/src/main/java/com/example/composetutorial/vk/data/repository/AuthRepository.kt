package com.example.composetutorial.vk.data.repository

import android.app.Application
import com.example.composetutorial.vk.domain.VKAuthState
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class AuthRepository(application: Application) {


    private val storage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(storage)

   private val scope = CoroutineScope(Dispatchers.Default)
   private val trigger = MutableSharedFlow<Unit>(replay = 1)

    val authState = flow<VKAuthState> {
        trigger.emit(Unit)
        trigger.collect {
            val tokenValue = token
            emit(
                if (tokenValue != null && tokenValue.isValid) VKAuthState.IsAuthorized
                else VKAuthState.IsNotAuthorized
            )
        }
    }.stateIn(
        started = SharingStarted.Lazily,
        initialValue = VKAuthState.Initial,
        scope = scope
    )


   suspend fun authTrigger() {
        trigger.emit(Unit)
    }


}