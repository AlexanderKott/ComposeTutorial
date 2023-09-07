package com.example.composetutorial.sumin.vk.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composetutorial.R
import com.example.composetutorial.vk.domain.PostItem
import com.example.composetutorial.vk.domain.StatisticType
import com.example.composetutorial.vk.domain.StatisticsItem
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun PostCaption(post: PostItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 7.dp, top = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = post.postAva,
            contentDescription = "",
            modifier = Modifier
                .size(50.dp),

            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .weight(3f)
                .padding(start = 4.dp)
        ) {
            Text(text = post.name)
            Text(text = post.time)
        }

        Icon(
            painter = painterResource(id = R.drawable.baseline_more_vert_24),
            contentDescription = "",
            modifier = Modifier,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}




@Composable
fun PostFooterPair(
    text: String, icon: Int,
    buttonColor: Color = MaterialTheme.colorScheme.secondary,
    click: () -> Unit,
) {
    Spacer(modifier = Modifier.width(13.dp))
    Text(text = text)
    Spacer(modifier = Modifier.width(3.dp))
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "",
        tint = buttonColor,
        modifier = Modifier.clickable { click() }
    )

}

@Composable
fun PostFooter(
    post: com.example.composetutorial.vk.domain.PostItem,
    onViewClickListener: (com.example.composetutorial.vk.domain.StatisticsItem) -> Unit,
    onShareClickListener: (com.example.composetutorial.vk.domain.StatisticsItem) -> Unit,
    onCommentClickListener: (com.example.composetutorial.vk.domain.StatisticsItem) -> Unit,
    onLikeClickListener: (com.example.composetutorial.vk.domain.StatisticsItem) -> Unit
) {
    Row(Modifier.padding(top = 10.dp, bottom = 10.dp, end = 10.dp)) {


        fun List<StatisticsItem>.find(type: StatisticType) =
            this.find { it.type == type } ?: throw error("StatisticsItem не найден")


        val viewStatistics =
            post.statistics.find(com.example.composetutorial.vk.domain.StatisticType.VIEW)

        PostFooterPair(digitsShorter(viewStatistics.count), R.drawable.baseline_eye_24) {
            onViewClickListener(viewStatistics)
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        val replyStatistics =
            post.statistics.find(com.example.composetutorial.vk.domain.StatisticType.REPLY)
        PostFooterPair(digitsShorter(replyStatistics.count), R.drawable.baseline_shortcut_24) {
            onShareClickListener(replyStatistics)
        }

        val commentStatistics =
            post.statistics.find(StatisticType.COMMENT)

        PostFooterPair(digitsShorter(commentStatistics.count ), R.drawable.baseline_mode_comment_24) {
            onCommentClickListener(commentStatistics)
        }

        val likeStatistics =
            post.statistics.find(StatisticType.LIKE)


        var likeIcon: Int = R.drawable.baseline_favorite_border_24
        var color = MaterialTheme.colorScheme.secondary

        if (post.isLiked) {
            likeIcon = R.drawable.baseline_favorite_24
            color = Color.Red
        }


        PostFooterPair(digitsShorter(likeStatistics.count) , likeIcon, color) {
            onLikeClickListener(likeStatistics)
        }

    }
}

fun digitsShorter(value : Long) : String {
    return if (value > 100_000) {
        String.format("%sK", (value / 1000))
    } else if (value > 1000) {
        String.format("%.1fK", (value / 1000f))
    } else {
        value.toString()
    }
}

@Composable
fun PostBody(post: com.example.composetutorial.vk.domain.PostItem) {
    Text(
        modifier = Modifier.padding(all = 4.dp),
        text = post.text,
        overflow = TextOverflow.Ellipsis
    )


    AsyncImage(
        model = post.imageAttach,
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        contentScale = ContentScale.FillWidth,
    )

}

