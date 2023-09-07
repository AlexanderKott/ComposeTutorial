package com.example.composetutorial.sumin.vk

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composetutorial.R
import com.example.composetutorial.vk.navigation.Screen

sealed class BottomNavigationItem(val route : String,
                                  val icon: ImageVector,
                                  val textId: Int) {

    object Home : BottomNavigationItem(
        Screen.HomeScreen.route,
        icon = Icons.Outlined.Home,
        textId = R.string.home
    )

    object Comments : BottomNavigationItem(
        Screen.CommentsScreen.route,
        icon = Icons.Outlined.Comment,
        textId = R.string.favor
    )


    object Favorite : BottomNavigationItem(
        Screen.FavoriteScreen.route,
        icon = Icons.Outlined.Favorite,
        textId = R.string.favor
    )

    object Profile : BottomNavigationItem(
        Screen.ProfileScreen.route,
        icon = Icons.Outlined.Edit,
        textId = R.string.profile
    )



}