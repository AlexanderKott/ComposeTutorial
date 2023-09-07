package com.example.composetutorial.sumin.instagram

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.composetutorial.vk.domain.AssetParamType
import com.example.composetutorial.vk.domain.PostItem
import com.example.composetutorial.vk.navigation.Screen


fun NavGraphBuilder.homeNavHostGraph(
    ribbon: @Composable () -> Unit,
    comments: @Composable (PostItem) -> Unit,

    ) {
    navigation(
        startDestination = Screen.FeedScreen.route,
        route = Screen.HomeScreen.route  //имя вложенного графа навигации
    ) {
        composable(Screen.FeedScreen.route) {
            ribbon()
        }

        composable(Screen.CommentsScreen.route,
            arguments = listOf(
                navArgument(Screen.CommentsScreen.KEY_COMMENTS_POST_ID) {
                    type = AssetParamType()
                }
            )
            ) {
            val post = it.arguments?.getParcelable<PostItem>(Screen.CommentsScreen.KEY_COMMENTS_POST_ID) ?:
            error("My PostItem serialization exception")
            comments(post)
        }

    }

}