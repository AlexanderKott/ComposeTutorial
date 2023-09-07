package com.example.composetutorial.sumin.vk.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composetutorial.vk.domain.PostItem
import com.example.composetutorial.vk.domain.StatisticsItem

@Composable
fun VKCard(
    post: PostItem,
    onViewClickListener: ( StatisticsItem) -> Unit,
    onShareClickListener: ( StatisticsItem) -> Unit,
    onCommentClickListener: ( StatisticsItem) -> Unit,
    onLikeClickListener: ( StatisticsItem) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        //   border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceTint),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column {

            PostCaption(post = post)
            PostBody(post)
            PostFooter(
                post,
                onViewClickListener = onViewClickListener,
                onShareClickListener = onShareClickListener,
                onCommentClickListener = onCommentClickListener,
                onLikeClickListener = onLikeClickListener
            )


        }

    }

}
