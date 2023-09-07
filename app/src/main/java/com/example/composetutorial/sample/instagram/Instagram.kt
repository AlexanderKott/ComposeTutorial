package com.example.composetutorial.sample.instagram

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.composetutorial.R


@Composable
fun VKTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val LightColorScheme = lightColorScheme(
        primary = MaterialTheme.colorScheme.primary,
        onPrimary = MaterialTheme.colorScheme.onPrimary,
        primaryContainer = MaterialTheme.colorScheme.surface,
        onPrimaryContainer = MaterialTheme.colorScheme.onSurface,
    )
    val DarkColorScheme = darkColorScheme(
        primary = MaterialTheme.colorScheme.primary,
        onPrimary = MaterialTheme.colorScheme.onPrimary,
        primaryContainer = MaterialTheme.colorScheme.surface,
        onPrimaryContainer = MaterialTheme.colorScheme.onSurface,
    )


    val colorScheme =
        if (!useDarkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        }
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}


@Composable
private fun TwoBox(text1: String, text2: String) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontFamily = FontFamily.Cursive,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(text1)
                }
            })
            Text(text = text2)
        }
    }
}

@Composable
fun InstaCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .padding(all = 8.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
        shape = RoundedCornerShape(10.dp).copy(
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        ),
    ) {
        content()
    }
}


@Composable
fun InstagramScreen(vm: InstagramViewModel) {
 Log.e("comz", "InstagramScreen")

    val isFollowing = vm.isFollowing.observeAsState(false)
        //rememberSaveable { mutableStateOf(false) }

    InstaCard {
        Log.e("comz", "InstaCard")
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(all = 5.dp)
                .fillMaxWidth()
        ) {

            Column(Modifier.fillMaxWidth()) {
                Followers()
                Spacer(modifier = Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    Column {
                        Links()

                        FollowBtn(isFollowing) {
                            vm.changeFollowState()
                        }


                    }
                }
            }
        }
    }

}

@Composable
private fun FollowBtn(isFollowing: State<Boolean>, onClickListener: () -> Unit ) {
    Log.e("comz", "FollowBtn")
    Button(onClick = {
        onClickListener()
    },
        shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.buttonColors(
        containerColor = if (isFollowing.value)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        else MaterialTheme.colorScheme.primary
    )

    ) {
        if (isFollowing.value)
            Text(text = "UnFollow")
        else
            Text(text = "Follow")
    }
}

@Composable
private fun Links() {
    Text(text = "Instagram", fontFamily = FontFamily.Cursive, fontSize = 40.sp)
    Text(text = "#YoursToMake")
    Text(text = "vk.com/mypageKot12345")
}

@Composable
private fun Followers() {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(id = R.drawable.instagram_2016_6),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(5.dp)
                    .size(45.dp),
                contentScale = ContentScale.Fit
            )


            TwoBox("9,001", "Posts")
            TwoBox("307M", "Followers")
            TwoBox("123", "Followings")

        }

    }
}