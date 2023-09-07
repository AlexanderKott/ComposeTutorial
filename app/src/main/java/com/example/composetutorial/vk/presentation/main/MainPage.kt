package com.example.composetutorial.sumin

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composetutorial.sumin.instagram.AppNavHostGraph
import com.example.composetutorial.sumin.vk.BottomNavigationItem
import com.example.composetutorial.vk.presentation.post.postRibbon
import com.example.composetutorial.vk.presentation.comments.CommentPage
import com.example.composetutorial.vk.navigation.Screen
import com.example.composetutorial.vk.navigation.createNavState
import com.google.gson.Gson
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun VKFeedPage() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = true,
                        onClick = {},
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = { DrawerContent(drawerState) }

    )
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DrawerContent(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    val sbstate = remember { SnackbarHostState() }
    val showFAB = rememberSaveable { mutableStateOf(true) }

    var scrollUp: LazyListState? = null


    val navigationState = createNavState()
    val tempButtonScar = remember { mutableStateOf(BottomNavigationItem.Home.route) }

    Scaffold(

        floatingActionButton = {
            if (showFAB.value && tempButtonScar.value == BottomNavigationItem.Home.route) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            scrollUp?.scrollToItem(0)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = null)

                }
            }
        },

        snackbarHost = { SnackbarHost(hostState = sbstate) },

        content = { paddingValues ->

            AppNavHostGraph(
                navHostController = navigationState.navController,
                feedpage = {
                     scrollUp = postRibbon(paddingValues, drawerState) { post ->
                        val json = Uri.encode(Gson().toJson(post))
                        navigationState.navigate(Screen.CommentsScreen.routeWithParam(json))
                    }
                },


                favorite = {
                    val countSecondScreenTest = rememberSaveable { mutableStateOf(0) }
                    Text(
                        modifier = Modifier
                            .clickable { countSecondScreenTest.value++ },
                        text = "Favorite ${countSecondScreenTest.value}"
                    )
                },

                profile = {
                    Text(
                        text = "Profile"
                    )

                }, commentspage = { postId ->
                    CommentPage(postId) {
                        navigationState.navController.popBackStack()
                    }
                }
            )

        },

        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground

            ) {

                val stateBackStack by navigationState.navController.currentBackStackEntryAsState()
                val currentRoute = stateBackStack?.destination?.route

                val items = listOf(
                    BottomNavigationItem.Home,
                    BottomNavigationItem.Favorite,
                    BottomNavigationItem.Profile
                )




                items.forEach { menuItem ->

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                menuItem.icon,
                                contentDescription = ""
                            )
                        },

                        selected = when {
                            currentRoute == menuItem.route -> true

                            BottomNavigationItem.Home.route == menuItem.route &&
                                    currentRoute == Screen.FeedScreen.route -> true

                            BottomNavigationItem.Home.route == menuItem.route &&
                                    currentRoute == Screen.CommentsScreen.route -> true

                            else -> false
                        },

                        onClick = {
                            if (currentRoute == Screen.CommentsScreen.route
                                && BottomNavigationItem.Home.route == menuItem.route
                            ) return@BottomNavigationItem

                            navigationState.navigate(menuItem.route)
                            tempButtonScar.value = menuItem.route

                        },

                        label = {
                            Text(stringResource(id = menuItem.textId))
                        }
                    )
                }

            }
        }
    )
}


