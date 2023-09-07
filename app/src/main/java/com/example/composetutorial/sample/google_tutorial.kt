package com.example.composetutorial

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.vk.presentation.main.Message

@Preview
@Composable
fun MultTable(){

    Column ( modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)){
        for (i in 1 until 10){
            Row (modifier = Modifier
                .fillMaxWidth()
                .weight(1f)){
                for (j in 1 until 10){
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(
                            if ((j + i) % 2 == 1) Color.Green else Color.White
                        ).border( width = 1.dp, color = Color.Black),

                        contentAlignment =   Alignment.Center
                    ) {
                        Column {
                            Text(text = "${j * i}")
                        }

                    }

                }
            }
        }
    }
}


@Composable
fun UserInfo(name : String, city : String){
    Column {
        repeat(10) {
            Text(text = "Привет, $name, ты из $city")
        }
    }

}



@Composable
fun conversation(list: List<Message>) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = list) { mess ->
            card(user = mess.author, message = mess.message)
        }
    }
}



@Composable
fun card(user: String  , message: String ) {
    Row(modifier = Modifier.padding(all = 10.dp)) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_launcher_background
            ),
            contentDescription = "Profile description",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)

        )

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = "hello, $user", color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 5.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)

            ) {
                Text(
                    text = message, style = MaterialTheme.typography.titleMedium,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }

        }
    }
}