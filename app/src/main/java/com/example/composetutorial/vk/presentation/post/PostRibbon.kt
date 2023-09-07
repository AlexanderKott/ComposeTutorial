package com.example.composetutorial.vk.presentation.post

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.material.Icon
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composetutorial.R
import com.example.composetutorial.sumin.vk.presentation.VKCard
import com.example.composetutorial.vk.domain.PostItem

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun postRibbon(
    paddingValuesParent: PaddingValues,
    drawerState: androidx.compose.material3.DrawerState, openComments: (PostItem) -> Unit
): LazyListState {
    val vm: VKPostsViewModel = viewModel()
    val posts = vm.screenStateFlow.collectAsState(FeedScreenState.LoadingState)
    val scope = rememberCoroutineScope()
    val stateLazy = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Лента постов") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(
                            Icons.Filled.Menu, contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            )
        },

        ) { paddingValues ->
        LazyColumn(
            state = stateLazy,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = paddingValuesParent.calculateBottomPadding(),
                    top = paddingValues.calculateTopPadding()
                )
        )
        {


            when (posts.value) {
                is FeedScreenState.Posts -> {
                    val po = (posts.value as FeedScreenState.Posts)

                    items(po.posts, key = { it.id }) { post ->
                        val dismissState = rememberDismissState()
                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            vm.deleteItem(post.id)
                        }

                        SwipeToDismiss(
                            modifier = Modifier.animateItemPlacement(),
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),

                            background = {
                                Box(
                                    modifier = Modifier
                                        .padding(25.dp)
                                        .background(
                                            color = Color.Red
                                                .copy(alpha = 0.7f)
                                        )
                                        .fillMaxSize()
                                        .height(40.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Text(
                                        modifier = Modifier.padding(end = 10.dp),
                                        text = "Удалить?"
                                    )
                                }

                            }

                        ) {
                            VKCard(post,
                                onViewClickListener = { sti ->
                                    // vm.onStatItemClick(sti, post.id)
                                },
                                onShareClickListener = { sti ->
                                    // vm.onStatItemClick(sti, post.id)
                                },
                                onCommentClickListener = { sti ->
                                    //Comment
                                    openComments(post)
                                },
                                onLikeClickListener = { sti ->
                                    vm.setLike(post)
                                }
                            )
                        }
                    }

                    item {
                        if (po.dataIsLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(15.dp),
                                contentAlignment = Alignment.Center
                            )
                            {
                                CircularProgressIndicator()
                            }


                        } else {
                            SideEffect {
                                vm.loadNextRecommendations()
                            }

                        }


                    }

                }

                is FeedScreenState.Error -> {
                    item {
                        Text(stringResource(R.string.no_internet_error))
                    }

                }
                is FeedScreenState.LoadingState -> {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )  {

                                CircularProgressIndicator()

                        }


                    }
                }
                else -> {  }
            }
         }
        }
        return stateLazy
    }


