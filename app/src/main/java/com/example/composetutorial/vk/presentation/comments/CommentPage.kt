package com.example.composetutorial.vk.presentation.comments

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.composetutorial.R
import com.example.composetutorial.vk.domain.CommentItem
import com.example.composetutorial.vk.domain.CommentsState
import com.example.composetutorial.vk.domain.PostItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentPage(post: PostItem, doBack: () -> Unit) {
    val vm: VKCommentsViewModel = viewModel()
    val comments = vm.comments.collectAsState(initial = CommentsState.Init )
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.Comments_caption)) },
                navigationIcon = {
                    IconButton(onClick = {
                        when (comments.value) {
                            is CommentsState.Success -> { doBack() }
                            else -> { Toast.makeText(context ,
                                 R.string.comments_no_internet  ,
                                Toast.LENGTH_LONG).show()
                            }
                        }

                         }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                })
        }

    ) { paddingValues ->
        BackHandler {
            doBack()
        }

        SideEffect {
            vm.getComments(post)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    paddingValues
                )
        ) {


            when (comments.value) {
                is CommentsState.Error -> {
                    item {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = stringResource(R.string.comments_no_internet)
                        )
                    }
                }

                is CommentsState.Success -> {
                    val cvalue = comments.value as CommentsState.Success

                    if (cvalue.list.isNotEmpty()) {
                        items(items = cvalue.list, key = { it.id }) {
                            CommentItemBlock(it)
                        }
                    } else {
                        item {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = stringResource(R.string.no_comments)
                            )
                        }
                    }
                }

                else -> {}
            }
    }
    }
}

@Composable
fun CommentItemBlock(item: CommentItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        AsyncImage(
            model = item.avatarId,
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(7.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(item.date, color = Color.DarkGray, fontSize = 15.sp)
            Text(item.authorName)
            Text(item.commentText)
        }
    }
}