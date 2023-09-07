package com.example.composetutorial.sumin.instagram

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composetutorial.vk.domain.PostItem
import com.example.composetutorial.vk.navigation.Screen

@Composable
fun AppNavHostGraph(
    navHostController: NavHostController,
    favorite: @Composable () -> Unit,
    profile: @Composable () -> Unit,

    feedpage: @Composable () -> Unit,
    commentspage: @Composable (PostItem) -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {

        homeNavHostGraph(
            ribbon = feedpage,
            comments = commentspage
        )

        composable(Screen.FavoriteScreen.route) {
            favorite()
        }

        composable(Screen.ProfileScreen.route) {
            profile()
        }
    }

}