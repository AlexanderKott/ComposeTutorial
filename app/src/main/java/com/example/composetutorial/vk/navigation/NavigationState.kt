package com.example.composetutorial.vk.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.sumin.vk.BottomNavigationItem

class NavigationState(val navController: NavHostController){


    fun navigate(route : String){
        navController.navigate(route) {
            when(route){
                Screen.CommentsScreen.route -> {}

                else -> {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            }


            launchSingleTop = true
            restoreState = true
        }
    }

}


@Composable
fun createNavState() : NavigationState {
    val navControllerx = rememberNavController()
    return remember {  NavigationState(navControllerx)  }
}